package org.scoula.sectionGeo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionGeoDTO {
    private String city;
    private String gu;
    private String dong;
    private String x;
    private String y;
    private String admCd;
}
