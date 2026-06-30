import os
import sys

PROJECT_ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

if PROJECT_ROOT not in sys.path:
    sys.path.append(PROJECT_ROOT)

from train_utils import run_training_pipeline, save_artifacts


ITEM_SETTINGS = {
    "HR": {
        "demand_col": "HR_demand",
        "target_type": "diff",
        "features": [
            "HR_demand_diff",
            "HR_demand",
            "month_diff",
            "steel_capacity_idx_diff",
            "CR_demand",
            "CR_demand_diff",
            "construction_order_amt_lag2",
            "month_lag2",
            "HR_inv_diff",
            "month_lag1",
            "HR_prod_ma3",
            "auto_domestic_ship",
            "construction_order_amt_diff_shock90",
            "auto_domestic_ship_diff_shock90",
            "is_month_12",
        ],
    },
    "CR": {
        "demand_col": "CR_demand",
        "target_type": "diff",
        "features": [
            "CR_demand",
            "month_diff",
            "CR_inv_diff",
            "usdkrw_avg",
            "appliance_prod_idx",
            "appliance_prod_idx_lag1",
            "CR_prod_lag2",
            "G_demand",
            "appliance_prod_idx_lag2",
            "leading_idx_diff",
            "usdkrw_avg_diff",
            "month_lag2",
            "CR_prod",
        ],
    },
    "GI": {
        "demand_col": "G_demand",
        "target_type": "rate",
        "features": [
            "is_month_1",
            "month_diff",
            "G_demand",
            "G_demand_diff",
            "is_month_12",
            "month_lag1",
            "steel_capacity_idx_diff",
            "auto_export_ship_diff_shock90",
            "CR_demand_diff_shock90",
            "appliance_prod_idx_diff_shock90",
            "month",
            "appliance_ship_idx_lag1",
            "auto_prod_diff",
            "auto_export_ship",
            "auto_prod",
            "appliance_prod_idx_lag1",
            "auto_export_ship_lag2",
            "auto_prod_lag2",
            "auto_domestic_ship_diff_shock90",
            "HR_inv",
        ],
    },
}


def main():
    for item_code, settings in ITEM_SETTINGS.items():
        result = run_training_pipeline(
            item_code=item_code,
            demand_col=settings["demand_col"],
            features=settings["features"],
            target_type=settings["target_type"],
            forecast_horizon=1,
            data_path="data/raw/steel_demand.csv",
            n_splits=4,
            test_size=10,
            save=False,
        )

        save_artifacts(
            item_code=item_code,
            feature_df=result["feature_df"],
            final_model=result["best_model"],
            features=result["features"],
            metrics=result["metrics"],
            prediction_history_df=result["predictions"],
            importance_df=result["importances"],
        )


if __name__ == "__main__":
    main()
