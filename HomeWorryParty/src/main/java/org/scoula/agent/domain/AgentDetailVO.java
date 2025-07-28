package org.scoula.agent.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentDetailVO {
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
}
