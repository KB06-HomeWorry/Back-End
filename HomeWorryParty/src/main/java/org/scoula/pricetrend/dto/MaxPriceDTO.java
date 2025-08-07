package org.scoula.pricetrend.dto;

import lombok.Data;

@Data
public class MaxPriceDTO {
    private String dongName;
    private Long maxPrice;
    private Double latitude;
    private Double longitude;
    private String formattedPrice;
}
