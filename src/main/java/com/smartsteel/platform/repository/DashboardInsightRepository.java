package com.smartsteel.platform.repository;

import com.smartsteel.platform.entity.DashboardInsight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DashboardInsightRepository
        extends JpaRepository<DashboardInsight, Long> {

    List<DashboardInsight> findByTargetMonth(String targetMonth);

}