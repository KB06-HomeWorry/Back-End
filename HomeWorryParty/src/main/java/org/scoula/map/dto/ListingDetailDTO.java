package org.scoula.map.dto;

public class ListingDetailDTO {
    private Long id;
    private String name;            // 매물명
    private String type;            // 원룸/오피스텔 등
    private String dealType;        // 전세, 월세, 매매
    private String rentalCondition; // 보증금/월세 등
    private String area;            // 전용면적
    private String floorInfo;       // 층수 (ex: 3/5층)
    private String direction;       // 방향 (ex: 남향)
    private String description;     // 상세 설명
    private String address;         // 주소
    private Double latitude;
    private Double longitude;
}
