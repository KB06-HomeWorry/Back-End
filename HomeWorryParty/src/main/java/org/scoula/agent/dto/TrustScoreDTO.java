package org.scoula.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrustScoreDTO {
    private Double totalTrustScore;
    private Double listingAccuracyScore;
    private Double costTransparencyScore;
    private Double professionalismScore;
    private Double accountabilityScore;
}
