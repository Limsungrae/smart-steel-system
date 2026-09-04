package com.smartsteel.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
    private String targetMonth;

    @Column(nullable = false)
    private Double totalForecastDemand;

    @Column(nullable = false)
    private Double totalCurrentStock;

    @Column(nullable = false)
    private Double totalShortage;

    @Column(nullable = false)
    private Integer highRiskCount;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void createTime() {
        createdAt = LocalDateTime.now();
    }
}
