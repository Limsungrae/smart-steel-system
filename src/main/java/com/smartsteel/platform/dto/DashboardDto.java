package com.smartsteel.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ==========================================================
 * 메인 대시보드 화면의 특수 컴포넌트(차트, 인사이트)를 위한 DTO
 * ==========================================================
 */
public class DashboardDto {

    // 📊 1. [전월 대비 품목별 수요 변화] 바 차트 데이터 매핑용
    @Getter
    @AllArgsConstructor
    public static class BarChartData {
        private String itemName;      // 품목명 (th:text="${chart.itemName}")
        private Double changeRate;    // 증감률 (th:text="${...changeRate}%")
        private Integer barHeight;    // 그래프 높이 퍼센트 (th:style="|height: ${chart.barHeight}%|")
    }

    // 💡 2. [수요 인사이트] 피드 데이터 매핑용
    @Getter
    @AllArgsConstructor
    public static class InsightData {
        private String type;          // 아이콘 분기용 (TREND, BULLSEYE, DATABASE 등)
        private String message;       // 인사이트 메시지 본문 (th:text="${insight.message}")
    }

    // 🚨 3. [재고 부족 위험 TOP 5] 프로그레스 바 매핑용
    @Getter
    @AllArgsConstructor
    public static class TopRiskData {
        private String itemName;         // 품목명 (th:text="${rank.itemName}")
        private Integer progressPercent; // 게이지바 길이 (th:style="|width: ${rank.progressPercent}%|")
        private Double shortageAmount;   // 부족량 수치 (th:text="${rank.shortageAmount}")
    }
}