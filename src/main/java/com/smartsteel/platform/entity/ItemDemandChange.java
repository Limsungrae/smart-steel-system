package com.smartsteel.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_demand_change")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDemandChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String targetMonth;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Double changeRate;

    @Column(nullable = false)
    private Integer barHeight;
}
