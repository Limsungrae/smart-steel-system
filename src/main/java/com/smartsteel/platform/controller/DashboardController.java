package com.smartsteel.platform.controller;

import com.smartsteel.platform.entity.ForecastSummary;
import com.smartsteel.platform.entity.ItemRiskStatus;
import com.smartsteel.platform.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        ForecastSummary summary = dashboardService.getSummary();

        if (summary != null) {
            model.addAttribute("targetMonth", summary.getTargetMonth());
            model.addAttribute("totalForecastDemand", summary.getTotalForecastDemand());
            model.addAttribute("totalCurrentStock", summary.getTotalCurrentStock());
            model.addAttribute("totalShortage", summary.getTotalShortage());
            model.addAttribute("highRiskCount", summary.getHighRiskCount());
        }

        List<ItemRiskStatus> riskStatus = dashboardService.getRiskStatus();

        model.addAttribute("riskTableData", riskStatus);
        model.addAttribute("barChartData", dashboardService.getChartData());
        model.addAttribute("insights", dashboardService.getInsights());

        int high = 0;
        int normal = 0;
        int low = 0;

        for (ItemRiskStatus item : riskStatus) {
            if ("높음".equals(item.getRiskGrade())) {
                high++;
            } else if ("보통".equals(item.getRiskGrade())) {
                normal++;
            } else {
                low++;
            }
        }

        model.addAttribute("highRiskCount", high);
        model.addAttribute("normalRiskCount", normal);
        model.addAttribute("keepPlanCount", low);
        model.addAttribute(
                "noticeMessage",
                "AI 분석 결과 일부 품목의 재고 부족 위험이 감지되었습니다."
        );

        // TODO: Replace hard-coded labels with month values from forecast history.
        model.addAttribute(
                "chartMonthLabels",
                List.of(
                        "24.09",
                        "24.10",
                        "24.11",
                        "24.12",
                        "25.01",
                        "25.02",
                        "25.03",
                        "25.04",
                        "25.05"
                )
        );

        // TODO: Sort by risk score and limit to the top five items.
        model.addAttribute("topRiskData", riskStatus);

        return "dashboard";
    }
}
