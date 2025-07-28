package org.scoula.map.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceTrendDetailDTO {
    private Long id;
    private String buildingName;
    private String dealType;
    private Integer price;
    private String contractDay;
    private String tradeMethod; //거래 방식

    //매물 정보
    private Float archArea; //면적
    private Integer builtYear; //사용승인일
    private String floor;

    // 위치 정보
    private String address;
    private Double latitude;
    private Double longitude;
}
