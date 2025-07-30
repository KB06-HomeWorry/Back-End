package org.scoula.agent.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeGeographyVO {
    private Long officeId;
    private String gu;
    private String dong;
    private String lat;
    private String lng;
}
