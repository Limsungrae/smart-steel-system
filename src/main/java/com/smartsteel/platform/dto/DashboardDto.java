package com.smartsteel.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DashboardDto {

    @Getter
    @AllArgsConstructor
    public static class BarChartData {
        private String itemName;
        private Double changeRate;
        private Integer barHeight;
    }

    @Getter
    @AllArgsConstructor
    public static class InsightData {
        private String type;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public static class TopRiskData {
        private String itemName;
        private Integer progressPercent;
        private Double shortageAmount;
    }
}
