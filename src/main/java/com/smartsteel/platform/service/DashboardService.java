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

    private final String TARGET_MONTH = "2026-05";

    public ForecastSummary getSummary() {

        return forecastSummaryRepository
                .findTopByTargetMonthOrderByIdDesc(TARGET_MONTH)
                .orElse(null);

    }

    public List<ItemRiskStatus> getRiskStatus() {

        return itemRiskStatusRepository
                .findByTargetMonth(TARGET_MONTH);

    }

    public List<ItemDemandChange> getChartData() {

        return itemDemandChangeRepository
                .findByTargetMonth(TARGET_MONTH);

    }

    public List<DashboardInsight> getInsights() {

        return dashboardInsightRepository
                .findByTargetMonth(TARGET_MONTH);

    }

}