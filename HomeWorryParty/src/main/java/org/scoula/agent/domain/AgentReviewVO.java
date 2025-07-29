package org.scoula.agent.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentReviewVO {
    private Long reviewId;
    private Long officeId;
    private Long userId;
    private String comment;
    private LocalDateTime createdAt;
    private int listingAccuracyScore;
    private int costTransparencyScore;
    private int professionalismScore;
    private int accountabilityScore;
}
