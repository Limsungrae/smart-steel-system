import os
from urllib.parse import quote_plus

import pandas as pd
from sqlalchemy import create_engine

from src.service import (
    get_monitoring_context,
    run_plan_risk_monitoring,
)


def create_db_engine():
    """Create a SQLAlchemy engine from environment variables."""
    host = os.getenv("DB_HOST", "localhost")
    port = os.getenv("DB_PORT", "3306")
    database = os.getenv("DB_NAME", "smartsteel")
    username = os.getenv("DB_USERNAME")
    password = os.getenv("DB_PASSWORD")

    if not username or not password:
        raise RuntimeError(
            "DB_USERNAME and DB_PASSWORD environment variables are required."
        )

    db_url = (
        f"mysql+pymysql://{quote_plus(username)}:{quote_plus(password)}"
        f"@{host}:{port}/{database}?charset=utf8mb4"
    )
    return create_engine(db_url)


engine = create_db_engine()

print("===================================")
print(" AI Forecast Engine Start ")
print("===================================")

context = get_monitoring_context()
target_month = context["forecast_month"]

print(f"분석 대상 : {target_month}")

query = f"""
SELECT
    item_code,
    planned_production,
    current_stock,
    target_stock,
    market_share
FROM demand_input
WHERE target_month='{target_month}'
"""

df = pd.read_sql(query, engine)

if df.empty:
    raise RuntimeError("사용자 입력 데이터가 없습니다.")

input_rows = df.to_dict(orient="records")
result = run_plan_risk_monitoring(input_rows)

print("AI 계산 완료")

with engine.begin() as conn:
    conn.exec_driver_sql(
        "DELETE FROM forecast_summary WHERE target_month=%s",
        (target_month,),
    )
    conn.exec_driver_sql(
        "DELETE FROM item_risk_status WHERE target_month=%s",
        (target_month,),
    )
    conn.exec_driver_sql(
        "DELETE FROM item_demand_change WHERE target_month=%s",
        (target_month,),
    )
    conn.exec_driver_sql(
        "DELETE FROM dashboard_insight WHERE target_month=%s",
        (target_month,),
    )

print("기존 Dashboard 데이터 삭제 완료")

items = result["items"]
total_forecast = sum(item["company_ai_demand"] for item in items)
total_stock = sum(item["current_stock"] for item in items)
total_shortage = sum(item["stock_gap"] for item in items)
high_count = sum(1 for item in items if item["priority_level"] == "높음")

summary = pd.DataFrame(
    [
        {
            "target_month": target_month,
            "total_forecast_demand": total_forecast,
            "total_current_stock": total_stock,
            "total_shortage": total_shortage,
            "high_risk_count": high_count,
        }
    ]
)

summary.to_sql(
    "forecast_summary",
    engine,
    if_exists="append",
    index=False,
)

status_rows = []
for item in items:
    status_rows.append(
        {
            "target_month": target_month,
            "item_name": item["item_name"],
            "forecast_demand": item["company_ai_demand"],
            "current_stock": item["current_stock"],
            "shortage": item["stock_gap"],
            "risk_grade": item["priority_level"],
            "progress_percent": item["risk_score"],
        }
    )

pd.DataFrame(status_rows).to_sql(
    "item_risk_status",
    engine,
    if_exists="append",
    index=False,
)

chart_rows = []
for item in items:
    chart_rows.append(
        {
            "target_month": target_month,
            "item_name": item["item_name"],
            "change_rate": item["demand_change_rate"],
            "bar_height": min(abs(item["demand_change_rate"]) * 5, 100),
        }
    )

pd.DataFrame(chart_rows).to_sql(
    "item_demand_change",
    engine,
    if_exists="append",
    index=False,
)

insight_rows = []
for item in items:
    insight_rows.append(
        {
            "target_month": target_month,
            "type": "TREND",
            "message": item["reason"],
        }
    )

pd.DataFrame(insight_rows).to_sql(
    "dashboard_insight",
    engine,
    if_exists="append",
    index=False,
)

print("Dashboard 생성 완료")
