package com.smartsteel.platform.repository;

import com.smartsteel.platform.entity.ForecastSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForecastSummaryRepository
        extends JpaRepository<ForecastSummary, Long> {


    Optional<ForecastSummary>
    findTopByTargetMonthOrderByIdDesc(String targetMonth);

}