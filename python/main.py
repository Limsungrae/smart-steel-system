import pandas as pd
from sqlalchemy import create_engine

from src.service import (
    get_monitoring_context,
    run_plan_risk_monitoring
)

# ==========================================================
# MySQL 연결
# ==========================================================
DB_URL = "mysql+pymysql://root:1234@localhost:3306/smartsteel"
engine = create_engine(DB_URL)

print("===================================")
print(" AI Forecast Engine Start ")
print("===================================")

# ==========================================================
# 분석 대상 월
# ==========================================================
context = get_monitoring_context()
target_month = context["forecast_month"]

print(f"분석 대상 : {target_month}")

# ==========================================================
# demand_input 읽기
# ==========================================================
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
    raise Exception("사용자 입력 데이터가 없습니다.")

input_rows = df.to_dict(orient="records")

# ==========================================================
# AI 실행
# ==========================================================
result = run_plan_risk_monitoring(input_rows)

print("AI 계산 완료")

# ==========================================================
# 기존 결과 삭제
# ==========================================================
with engine.begin() as conn:

    conn.execute(
        f"DELETE FROM forecast_summary WHERE target_month='{target_month}'"
    )

    conn.execute(
        f"DELETE FROM item_risk_status WHERE target_month='{target_month}'"
    )

    conn.execute(
        f"DELETE FROM item_demand_change WHERE target_month='{target_month}'"
    )

    conn.execute(
        f"DELETE FROM dashboard_insight WHERE target_month='{target_month}'"
    )

print("기존 Dashboard 데이터 삭제 완료")

# ==========================================================
# Summary 저장
# ==========================================================

items = result["items"]

total_forecast = sum(i["company_ai_demand"] for i in items)

total_stock = sum(i["current_stock"] for i in items)

total_shortage = sum(i["stock_gap"] for i in items)

high_count = sum(
    1 for i in items
    if i["priority_level"] == "높음"
)

summary = pd.DataFrame([{

    "target_month": target_month,

    "total_forecast_demand": total_forecast,

    "total_current_stock": total_stock,

    "total_shortage": total_shortage,

    "high_risk_count": high_count

}])

summary.to_sql(
    "forecast_summary",
    engine,
    if_exists="append",
    index=False
)

print("forecast_summary 저장 완료")

# ==========================================================
# 품목 상태 저장
# ==========================================================

status_rows = []

for item in items:

    status_rows.append({

        "target_month": target_month,

        "item_name": item["item_name"],

        "forecast_demand": item["company_ai_demand"],

        "current_stock": item["current_stock"],

        "shortage": item["stock_gap"],

        "risk_grade": item["priority_level"],

        "progress_percent": item["risk_score"]

    })

status_df = pd.DataFrame(status_rows)

status_df.to_sql(
    "item_risk_status",
    engine,
    if_exists="append",
    index=False
)

print("item_risk_status 저장 완료")

# ==========================================================
# Bar Chart 저장
# ==========================================================

chart_rows = []

for item in items:

    chart_rows.append({

        "target_month": target_month,

        "item_name": item["item_name"],

        "change_rate": item["demand_change_rate"],

        "bar_height": min(
            abs(item["demand_change_rate"]) * 5,
            100
        )

    })

chart_df = pd.DataFrame(chart_rows)

chart_df.to_sql(
    "item_demand_change",
    engine,
    if_exists="append",
    index=False
)

print("item_demand_change 저장 완료")

# ==========================================================
# Insight 저장
# ==========================================================

insight_rows = []

for item in items:

    insight_rows.append({

        "target_month": target_month,

        "type": "TREND",

        "message": item["reason"]

    })

insight_df = pd.DataFrame(insight_rows)

insight_df.to_sql(
    "dashboard_insight",
    engine,
    if_exists="append",
    index=False
)

print("dashboard_insight 저장 완료")

print()
print("===================================")
print(" Dashboard 생성 완료 ")
print("===================================")