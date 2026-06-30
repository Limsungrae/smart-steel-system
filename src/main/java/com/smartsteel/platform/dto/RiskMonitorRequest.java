package com.smartsteel.platform.dto;

import lombok.Data;

@Data
public class RiskMonitorRequest {
    private String itemCode;          // HR, CR, GI
    private String itemName;          // 💡 추가: 열연강판, 냉연강판 등
    private String targetMonth;       // 💡 추가: 2026-05 (예측 기준월 필수)
    private Double plannedProduction; // 회사 기존 생산계획
    private Double currentStock;      // 회사 현재 재고
    private Double targetStock;       // 회사 목표 재고
    private Double marketShare;       // 회사 시장점유율 (%)
}