package org.scoula.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.agent.domain.AgentBookmarkVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentBookmarkDTO {
    private Long officeId;
    private String officeName;
    private String address;
    private String profileImage;

    public AgentBookmarkDTO of(AgentBookmarkVO vo){
        return AgentBookmarkDTO.builder()
                .officeId(vo.getOfficeId())
                .officeName(vo.getOfficeName())
                .address(vo.getAddress())
                .profileImage(vo.getProfileImage())
                .build();
    }

    public AgentBookmarkVO toVO(){
        return AgentBookmarkVO.builder()
                .officeId(officeId)
                .officeName(officeName)
                .address(address)
                .profileImage(profileImage)
                .build();
    }
}
