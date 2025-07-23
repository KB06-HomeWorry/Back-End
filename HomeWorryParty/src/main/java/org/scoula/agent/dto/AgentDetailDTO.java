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
    private String gu;
    private String dong;
    private String address;
    private String agentNumber;
    private String agentName;
    private String officeName;
    private String phone;

    public static AgentDetailDTO of(AgentDetailVO vo){
        return AgentDetailDTO.builder()
                .officeId(vo.getOfficeId())
                .gu(vo.getGu())
                .dong(vo.getDong())
                .address(vo.getAddress())
                .agentNumber(vo.getAgentNumber())
                .agentName(vo.getAgentName())
                .officeName(vo.getOfficeName())
                .phone(vo.getPhone())
                .build();
    }

    public AgentDetailVO toVO() {
        return AgentDetailVO.builder()
                .officeId(officeId)
                .gu(gu)
                .dong(dong)
                .address(address)
                .agentNumber(agentNumber)
                .agentName(agentName)
                .officeName(officeName)
                .phone(phone)
                .build();
    }
}
