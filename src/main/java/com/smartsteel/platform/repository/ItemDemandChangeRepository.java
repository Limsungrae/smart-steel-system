package com.smartsteel.platform.repository;

import com.smartsteel.platform.entity.ItemDemandChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDemandChangeRepository
        extends JpaRepository<ItemDemandChange, Long> {

    List<ItemDemandChange> findByTargetMonth(String targetMonth);

}