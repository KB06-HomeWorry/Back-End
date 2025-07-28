package org.scoula.agent.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.agent.dto.AgentDetailDTO;
import org.scoula.agent.dto.AgentReviewDTO;
import org.scoula.agent.dto.TrustScoreDTO;
import org.scoula.agent.service.AgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequestMapping("/api/agent")
@RestController
@RequiredArgsConstructor
public class AgentController {
    private final AgentService service;

    @GetMapping(value = "/fetch", produces = "text/plain;charset=UTF-8") // openAPI 에서 중개사 정보 받아와 DB에 저장
    public ResponseEntity<String> fetch(){
        return ResponseEntity.ok().body(service.fetchAndSaveOffice());
    }

    @GetMapping("/{officeId}") // 중개사 상세정보 조회
    public ResponseEntity<AgentDetailDTO> getAgentDetail(@PathVariable Long officeId){
        return ResponseEntity.ok().body(service.getAgentDetail(officeId));
    }

    @GetMapping("/reviews/{officeId}") // 중개사 리뷰 조회
    public ResponseEntity<List<AgentReviewDTO>> getAgentReviews(@PathVariable Long officeId){
        return ResponseEntity.ok().body(service.getAgentReviews(officeId));
    }

    @PostMapping("reviews") // 리뷰 작성
    public void writeAgentReview(@RequestBody AgentReviewDTO agentReviewDTO){
        service.writeAgentReview(agentReviewDTO);
    }

    @GetMapping("/trustScore/{officeId}") // 중개사 점수 조회
    public ResponseEntity<TrustScoreDTO> getAgentScore(@PathVariable Long officeId){
        return ResponseEntity.ok().body(service.getAgentScore(officeId));
    }
}
