package com.smartsteel.platform.dto;

import lombok.Data;

@Data
public class RiskMonitorRequest {
    private String itemCode;
    private String itemName;
    private String targetMonth;
    private Double plannedProduction;
    private Double currentStock;
    private Double targetStock;
    private Double marketShare;
}
