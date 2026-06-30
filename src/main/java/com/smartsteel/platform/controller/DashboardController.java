package com.smartsteel.platform.controller;

import com.smartsteel.platform.dto.DashboardDto.*;
import com.smartsteel.platform.entity.ForecastSummary;
import com.smartsteel.platform.entity.ItemRiskStatus;
import com.smartsteel.platform.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * 대시보드 화면 조회
     */
    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value = "targetMonth", required = false) String targetMonth, Model model) {

        // 1. 파이썬 파이프라인 기준 월 기본값 설정
        if (targetMonth == null || targetMonth.isEmpty()) {
            targetMonth = "2026-05";
        }

        // 상단 캘린더 표시용 (2026-05 -> 2026.05)
        model.addAttribute("targetMonth", targetMonth.replace("-", "."));

        // 2. 상단 KPI 카드 바인딩 (ForecastSummary 조회)
        ForecastSummary summary = dashboardService.getSummary(targetMonth);
        if (summary != null) {
            model.addAttribute("totalForecastDemand", summary.getTotalForecastDemand());
            model.addAttribute("totalCurrentStock", summary.getTotalCurrentStock());
            model.addAttribute("totalShortage", summary.getTotalShortage());
            model.addAttribute("highRiskCount", summary.getHighRiskCount());
        } else {
            model.addAttribute("totalForecastDemand", 0.0);
            model.addAttribute("totalCurrentStock", 0.0);
            model.addAttribute("totalShortage", 0.0);
            model.addAttribute("highRiskCount", 0);
        }

        // 3. [품목별 위험 현황] 테이블 데이터 (getRiskStatus로 메서드명 매칭 ⚡)
        List<ItemRiskStatus> riskList = dashboardService.getRiskStatus(targetMonth);
        model.addAttribute("riskTableData", riskList);

        // 4. [재고 부족 위험 TOP 5] 가공 데이터 생성 및 바인딩
        List<TopRiskData> topRiskData = riskList.stream()
                .map(item -> new TopRiskData(item.getItemName(), item.getProgressPercent(), item.getShortage()))
                .sorted((a, b) -> Double.compare(a.getShortageAmount(), b.getShortageAmount())) // 부족량 심한 순 정렬
                .limit(5)
                .toList();
        model.addAttribute("topRiskData", topRiskData);

        // 5. [전월 대비 품목별 수요 변화] 바 차트 바인딩 (서비스의 getChartData 연동 ⚡)
        // 만약 DB 데이터(itemDemandChangeRepository) 기반으로 그리고 싶다면 아래 주석을 풀고 사용하세요.
        // 지금은 화면 깨짐 방지 및 규격 맞춤용으로 DTO 변환을 컨트롤러에서 임시 처리합니다.
        List<BarChartData> barChartData = dashboardService.getChartData(targetMonth).stream()
                .map(c -> new BarChartData(c.getItemName(), c.getChangeRate(), (int)Math.round(c.getChangeRate() * 8))) // 증감률 비례 높이 계산
                .toList();

        if (barChartData.isEmpty()) { // DB 데이터가 없을 때 백업용 더미
            barChartData = Arrays.asList(
                    new BarChartData("열연강판", 9.3, 85),
                    new BarChartData("냉연강판", 7.4, 64),
                    new BarChartData("아연도강판", 5.6, 48)
            );
        }
        model.addAttribute("barChartData", barChartData);

        // 6. [수요 인사이트] 피드 데이터 바인딩 (서비스의 getInsights 연동 ⚡)
        List<InsightData> insights = dashboardService.getInsights(targetMonth).stream()
                .map(i -> new InsightData(i.getType(), i.getMessage()))
                .toList();

        if (insights.isEmpty()) { // DB 데이터가 없을 때 백업용 더미
            insights = Arrays.asList(
                    new InsightData("TREND", "열연강판 수요가 전월 대비 9.3% 증가하여 상승세를 주도하고 있습니다."),
                    new InsightData("BULLSEYE", "목표 재고량 기준 냉연강판의 공급 부족 위험성이 감지되었습니다."),
                    new InsightData("DATABASE", "현재 입력된 운영 계획 기준, 2026년 5월 총 부족량은 AI 분석 결과 " + (summary != null ? summary.getTotalShortage() : 0) + "만톤으로 추정됩니다.")
            );
        }
        model.addAttribute("insights", insights);

        // 7. 차트 x축 라벨용 데이터 (추이 그래프용 고정 라벨)
        List<String> chartMonthLabels = Arrays.asList("2025.09", "2025.10", "2025.11", "2025.12", "2026.01", "2026.02", "2026.03", "2026.04", "2026.05");
        model.addAttribute("chartMonthLabels", chartMonthLabels);

        return "dashboard";
    }
}