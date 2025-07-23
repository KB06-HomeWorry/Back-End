package org.scoula.agent.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentDetailVO {
    private Long officeId;
    private String gu;
    private String dong;
    private String address;
    private String agentNumber;
    private String agentName;
    private String officeName;
    private String phone;
}
