package com.smartsteel.platform.repository;

import com.smartsteel.platform.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository
        extends JpaRepository<Item, Long> {

}