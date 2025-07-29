package org.scoula.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.agent.domain.OfficeGeographyVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeGeographyDTO {
    private Long officeId;
    private String gu;
    private String dong;
    private String lat;
    private String lng;

    public OfficeGeographyDTO of(OfficeGeographyVO vo){
        return OfficeGeographyDTO.builder()
                .officeId(vo.getOfficeId())
                .gu(vo.getGu())
                .dong(vo.getDong())
                .lat(vo.getLat())
                .lng(vo.getLng())
                .build();
    }

    public OfficeGeographyVO toVO(){
        return OfficeGeographyVO.builder()
                .officeId(officeId)
                .gu(gu)
                .dong(dong)
                .lat(lat)
                .lng(lng)
                .build();
    }
}
