package org.scoula.agent.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentBookmarkVO {
    private Long officeId;
    private String officeName;
    private String address;
    private String profileImage;
}
