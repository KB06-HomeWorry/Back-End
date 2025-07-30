package org.scoula.listing.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListingVO {
    private int id;
    private String listing;
    private String rentalCondition;
    private String details;
    private String agency;
    private String address;
    private Double latitude;
    private Double longitude;
    private String transactionType;
    private Integer deposit;
    private Integer monthlyRent;
    private String housingType;
    private String areaInfo;
    private String floorInfo;
    private String direction;
}
