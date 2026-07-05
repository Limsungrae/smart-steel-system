package com.smartsteel.platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "forecast_result")
public class ForecastResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String targetMonth;

    private String itemCode;

    private Double nationalForecast;

    private Double companyForecast;

    private Double marketShare;

    private Double plannedProduction;

    private Double expectedEndStock;

    private Double targetStock;

    private Double stockGap;

    private Integer riskScore;

    private String riskGrade;

    @Column(length = 300)
    private String mainSignals;

    private String reviewDirection;

}