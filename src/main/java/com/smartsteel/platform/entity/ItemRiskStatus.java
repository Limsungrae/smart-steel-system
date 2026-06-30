package com.smartsteel.platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_risk_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRiskStatus {

    // ============================
    // 기본키
    // ============================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ============================
    // 예측 기준월
    // 예) 2026-05
    // ============================
    @Column(nullable = false)
    private String targetMonth;


    // ============================
    // 품목명
    // 예) 열연강판(HR)
    // ============================
    @Column(nullable = false)
    private String itemName;


    // ============================
    // 회사 기준 AI 예측 수요
    // ============================
    @Column(nullable = false)
    private Double forecastDemand;


    // ============================
    // 현재 재고
    // ============================
    @Column(nullable = false)
    private Double currentStock;


    // ============================
    // 목표 재고 대비 부족량
    // 음수이면 부족
    // ============================
    @Column(nullable = false)
    private Double shortage;


    // ============================
    // 위험등급
    // 높음 / 보통 / 낮음
    // ============================
    @Column(nullable = false)
    private String riskGrade;


    // ============================
    // 진행률(리스크 점수)
    // ProgressBar 표시용
    // ============================
    @Column(nullable = false)
    private Integer progressPercent;

}