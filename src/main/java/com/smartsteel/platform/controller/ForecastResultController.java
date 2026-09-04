package com.smartsteel.platform.controller;

import com.smartsteel.platform.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ForecastResultController {

    private final DashboardService dashboardService;

    @GetMapping("/forecast-result")
    public String result(Model model) {
        var summary = dashboardService.getSummary();
        var riskList = dashboardService.getRiskStatus();

        model.addAttribute("summary", summary);
        model.addAttribute("results", riskList);
        model.addAttribute("riskStatus", riskList);
        model.addAttribute("chartData", dashboardService.getChartData());
        model.addAttribute("insights", dashboardService.getInsights());

        if (summary != null) {
            model.addAttribute("highRiskCount", summary.getHighRiskCount());
            model.addAttribute("targetMonth", summary.getTargetMonth());
            model.addAttribute(
                    "normalRiskCount",
                    Math.max(0, riskList.size() - summary.getHighRiskCount())
            );
            model.addAttribute("keepPlanCount", riskList.size());
        }

        return "forecast-result";
    }
}
