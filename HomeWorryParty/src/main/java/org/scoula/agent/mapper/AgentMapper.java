package org.scoula.agent.mapper;

import org.apache.ibatis.annotations.Param;
import org.scoula.agent.domain.AgentDetailVO;
import org.scoula.agent.domain.AgentReviewVO;
import org.scoula.agent.dto.AgentDetailDTO;
import org.scoula.agent.dto.AgentReviewDTO;
import org.scoula.agent.model.Office;

import java.time.LocalDateTime;
import java.util.List;

public interface AgentMapper {
    void save(Office officeVO); // 중개사무소 정보 저장

    void saveAll(@Param("Offices") List<Office> offices); // 중개사무소 정보 전체 저장

    LocalDateTime findUpdatedAt(); // OpenAPI 데이터 업데이트 시간 조회

    void saveUpdateAt(); // OpenAPI 데이터 업데이트 시간 저장

    AgentDetailVO getAgentDetail(Long officeId); // 중개사무소 상세정보 조회

    List<AgentReviewVO> getAgentReviews(Long officeId); // 중개사무소 리뷰 전체 조회

    void writeAgentReview(AgentReviewVO agentReviewVO); // 중개사무소 리뷰 작성

    List<AgentDetailVO> findAgentByHouseAddress(String houseAddress); // 매물로 담당 중개사 탐색

    List<AgentDetailDTO> getAgentList(); // 중개사무소 리스트 조회

    void updateTrustScore(@Param("officeId") Long officeId,@Param("trustScore") double trustScore); // 중개사무소 신뢰점수 업데이트
}
