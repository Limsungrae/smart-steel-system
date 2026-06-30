package com.smartsteel.platform.service;

import com.smartsteel.platform.entity.*;
import com.smartsteel.platform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ForecastSummaryRepository forecastSummaryRepository;
    private final ItemRiskStatusRepository itemRiskStatusRepository;
    private final ItemDemandChangeRepository itemDemandChangeRepository;
    private final DashboardInsightRepository dashboardInsightRepository;

    /**
     * 대시보드 상단 요약 조회
     */
    public ForecastSummary getSummary(String targetMonth) {

        return forecastSummaryRepository
                .findByTargetMonth(targetMonth)
                .orElse(null);
    }

    /**
     * 품목별 리스크 조회
     */
    public List<ItemRiskStatus> getRiskStatus(String targetMonth) {

        return itemRiskStatusRepository
                .findByTargetMonth(targetMonth);
    }

    /**
     * 수요 변화 차트 조회
     */
    public List<ItemDemandChange> getChartData(String targetMonth) {

        return itemDemandChangeRepository
                .findByTargetMonth(targetMonth);
    }

    /**
     * AI 인사이트 조회
     */
    public List<DashboardInsight> getInsights(String targetMonth) {

        return dashboardInsightRepository
                .findByTargetMonth(targetMonth);
    }

}