package com.smartsteel.platform.repository;

import com.smartsteel.platform.entity.ForecastResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForecastResultRepository
        extends JpaRepository<ForecastResult,Long> {

    List<ForecastResult> findByTargetMonth(String targetMonth);

}