package com.smartsteel.platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_demand_change")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDemandChange {

    // ============================
    // 기본키
    // ============================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ============================
    // 예측 기준월
    // ============================
    @Column(nullable = false)
    private String targetMonth;


    // ============================
    // 품목명
    // ============================
    @Column(nullable = false)
    private String itemName;


    // ============================
    // 최근 평균 대비 수요 증감률(%)
    // ============================
    @Column(nullable = false)
    private Double changeRate;


    // ============================
    // 막대그래프 높이
    // (Frontend 표시용)
    // ============================
    @Column(nullable = false)
    private Integer barHeight;

}