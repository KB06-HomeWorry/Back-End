package org.scoula.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.agent.domain.AgentReviewVO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentReviewDTO {
    private Long reviewId;
    private Long officeId;
    private Long userId;
    private String userToken;
    private String comment;
    private LocalDateTime createdAt;
    private int listingAccuracyScore;
    private int costTransparencyScore;
    private int professionalismScore;
    private int accountabilityScore;

    public static AgentReviewDTO of(AgentReviewVO vo){
        return AgentReviewDTO.builder()
                .reviewId(vo.getReviewId())
                .officeId(vo.getOfficeId())
                .userId(vo.getUserId())
                .comment(vo.getComment())
                .createdAt(vo.getCreatedAt())
                .listingAccuracyScore(vo.getListingAccuracyScore())
                .costTransparencyScore(vo.getCostTransparencyScore())
                .professionalismScore(vo.getProfessionalismScore())
                .accountabilityScore(vo.getAccountabilityScore())
                .build();
    }

    public AgentReviewVO toVO(){
        return AgentReviewVO.builder()
                .reviewId(reviewId)
                .officeId(officeId)
                .userId(userId)
                .comment(comment)
                .createdAt(createdAt)
                .listingAccuracyScore(listingAccuracyScore)
                .costTransparencyScore(costTransparencyScore)
                .professionalismScore(professionalismScore)
                .accountabilityScore(accountabilityScore)
                .build();
    }
}
