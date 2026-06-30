package com.smartsteel.platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RiskMonitorResponse {
    private String itemCode;
    private String itemName;

    // 전국 및 회사 수요 지표
    private Double nationalForecastDemand;     // 전국 AI 예측 수요
    private Double companyForecastDemand;      // 회사 AI 예측 수요
    private Double companyAvgDemand;           // 최근 회사 평균 수요
    private Double demandChangeRate;           // 최근 평균 대비 증감률 (%)

    // 사용자가 입력한 계획 데이터 및 연산 결과
    private Double plannedProduction;          // 기존 계획
    private Double planGap;                    // 계획 차이
    private Double currentStock;               // 현재 재고
    private Double expectedMonthEndStock;      // 예상 월말 재고
    private Double stockGap;                   // 재고 차이
    private Double targetStock;                // 목표 재고

    // 리스크 결과 분석 지표
    private Double riskScore;                  // 최종 리스크 점수
    private String mainSignals;                // 주요 신호 (예: 계획 부족, 재고 부족)
    private String reviewDirection;            // 검토 방향 (예: 생산계획 상향 검토)
}