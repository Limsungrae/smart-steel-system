package com.smartsteel.platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dashboard_insight")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardInsight {

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
    // 메시지 종류
    // NOTICE
    // ============================
    @Column(nullable = false)
    private String type;


    // ============================
    // AI 분석 메시지
    // 예)
    // HR : 계획 부족, 재고 부족
    // ============================
    @Column(nullable = false, length = 500)
    private String message;

}