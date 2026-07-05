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

        // ==========================
        // 1. 요약 정보 조회
        // ==========================
        ForecastSummary summary = dashboardService.getSummary();

        if (summary != null) {

            model.addAttribute("targetMonth",
                    summary.getTargetMonth());

            model.addAttribute("totalForecastDemand",
                    summary.getTotalForecastDemand());

            model.addAttribute("totalCurrentStock",
                    summary.getTotalCurrentStock());

            model.addAttribute("totalShortage",
                    summary.getTotalShortage());

            model.addAttribute("highRiskCount",
                    summary.getHighRiskCount());
        }

        // ==========================
        // 2. 품목 위험 현황
        // ==========================
        List<ItemRiskStatus> riskStatus =
                dashboardService.getRiskStatus();

        model.addAttribute("riskTableData", riskStatus);

        // ==========================
        // 3. Bar Chart
        // ==========================
        model.addAttribute(
                "barChartData",
                dashboardService.getChartData());

        // ==========================
        // 4. AI 인사이트
        // ==========================
        model.addAttribute(
                "insights",
                dashboardService.getInsights());

        // ==========================
        // 5. 위험 등급 개수 계산
        // ==========================
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

        // ==========================
        // 6. 공지사항
        // ==========================
        model.addAttribute(
                "noticeMessage",
                "AI 분석 결과 일부 품목의 재고 부족 위험이 감지되었습니다."
        );

        // ==========================
        // 7. 월 라벨 (임시)
        // ==========================
        model.addAttribute(
                "chartMonthLabels",
                java.util.List.of(
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

        // ==========================
        // 8. TOP5 (현재는 동일 데이터 사용)
        // ==========================
        model.addAttribute(
                "topRiskData",
                riskStatus
        );

        return "dashboard";
    }

}