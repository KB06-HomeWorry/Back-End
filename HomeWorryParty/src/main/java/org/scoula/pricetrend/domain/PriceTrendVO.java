package org.scoula.pricetrend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceTrendVO {
    private int id;
    private Integer year;
    private String districtCode;
    private String districtName;
    private String dongCode;
    private String dongName;
    private String lotTypeCode;
    private String lotType;
    private String mainNo;
    private String subNo;
    private String buildingName;
    private String contractDay;
    private Long price;
    private Float archArea;
    private Float landArea;
    private String floor;
    private String builtYear;
    private String housingType;
    private String dealType;
    private String address;
    private Double latitude;
    private Double longitude;
}
