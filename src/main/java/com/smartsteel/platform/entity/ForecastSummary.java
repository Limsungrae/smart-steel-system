package com.smartsteel.platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "forecast_summary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForecastSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예측 기준월
    @Column(nullable = false)
    private String targetMonth;

    // 총 AI 예측수요
    @Column(nullable =false)
    private Double totalForecastDemand;

    // 총 현재재고
    @Column(nullable =false)
    private Double totalCurrentStock;

    // 총 부족예상량
    @Column(nullable =false)
    private Double totalShortage;

    // 위험품목 개수
    @Column(nullable =false)
    private Integer highRiskCount;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void createTime(){
        createdAt = LocalDateTime.now();
    }
}