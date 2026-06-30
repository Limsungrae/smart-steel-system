package com.smartsteel.platform.repository;

import com.smartsteel.platform.entity.ItemRiskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRiskStatusRepository
        extends JpaRepository<ItemRiskStatus, Long> {

    List<ItemRiskStatus> findByTargetMonth(String targetMonth);

}