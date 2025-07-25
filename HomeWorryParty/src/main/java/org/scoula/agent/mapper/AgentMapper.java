package org.scoula.agent.mapper;

import org.apache.ibatis.annotations.Param;
import org.scoula.agent.domain.AgentDetailVO;
import org.scoula.agent.domain.AgentReviewVO;
import org.scoula.agent.dto.AgentReviewDTO;
import org.scoula.agent.model.Office;

import java.time.LocalDateTime;
import java.util.List;

public interface AgentMapper {
    void save(Office officeVO);

    void saveAll(@Param("Offices") List<Office> offices);

    LocalDateTime findUpdatedAt();

    void saveUpdateAt();

    AgentDetailVO getAgentDetail(Long officeId);

    List<AgentReviewVO> getAgentReviews(Long officeId);

    void writeAgentReview(AgentReviewVO agentReviewVO);

    int updateAgentScore(@Param("officeId") Long officeId, @Param("trustScore") Long trustScore);
}
