package org.scoula.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.agent.domain.AgentDetailVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentDetailDTO {
    private Long officeId;
    private String address;
    private String licenseNumber;
    private String agentName;
    private String officeName;
    private String profileImage;
    private String phone;
    private String specialties;
    private String description;
    private int totalScore;

    public static AgentDetailDTO of(AgentDetailVO vo){
        return AgentDetailDTO.builder()
                .officeId(vo.getOfficeId())
                .address(vo.getAddress())
                .licenseNumber(vo.getLicenseNumber())
                .agentName(vo.getAgentName())
                .officeName(vo.getOfficeName())
                .profileImage(vo.getProfileImage())
                .phone(vo.getPhone())
                .specialties(vo.getSpecialties())
                .description(vo.getDescription())
                .totalScore(vo.getTotalScore())
                .build();
    }

    public AgentDetailVO toVO() {
        return AgentDetailVO.builder()
                .officeId(officeId)
                .address(address)
                .licenseNumber(licenseNumber)
                .agentName(agentName)
                .officeName(officeName)
                .profileImage(profileImage)
                .phone(phone)
                .specialties(specialties)
                .description(description)
                .totalScore(totalScore)
                .build();
    }
}
