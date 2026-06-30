package com.smartsteel.platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "demand_input")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandInput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예측 기준월
    @Column(nullable = false, length = 20)
    private String targetMonth;

    // HR / CR / GI
    @Column(nullable = false, length = 20)
    private String itemCode;

    // 열연강판
    @Column(nullable = false, length = 100)
    private String itemName;

    // 회사 생산계획
    @Column(nullable = false)
    private Double plannedProduction;

    // 현재 재고
    @Column(nullable = false)
    private Double currentStock;

    // 목표 재고
    @Column(nullable = false)
    private Double targetStock;

    // 시장점유율
    @Column(nullable = false)
    private Double marketShare;
}