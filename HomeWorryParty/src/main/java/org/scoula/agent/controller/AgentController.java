package org.scoula.agent.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.agent.dto.*;
import org.scoula.agent.service.AgentService;
import org.scoula.security.util.JwtProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequestMapping("/api/agent")
@RestController
@RequiredArgsConstructor
public class AgentController {
    private final AgentService service;
    private final JwtProcessor jwtProcessor;

    @GetMapping(value = "/fetch", produces = "text/plain;charset=UTF-8") // openAPI 에서 중개사 정보 받아와 DB에 저장
    public ResponseEntity<String> fetch(){
        return ResponseEntity.ok().body(service.fetchAndSaveOffice());
    }

    @GetMapping(value = "/fetchGeo", produces = "text/plain;charset=UTF-8") // 사무소 위치 정보 저장
    public ResponseEntity<?> fetchGeo(){
        return ResponseEntity.ok().body(service.saveOfficeGeography());
    }

    @GetMapping("/list") // 중개사 전체 목록 조회
    public ResponseEntity<List<AgentDetailDTO>> getList(){
        return ResponseEntity.ok().body(service.getAgentList());
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

    @GetMapping("/geo/{officeId}") // 사무소 위치 정보 조회
    public ResponseEntity<OfficeGeographyDTO> getOfficeGeo(@PathVariable Long officeId){
        return ResponseEntity.ok().body(service.getOfficeGeography(officeId));
    }

    @GetMapping("/geo/list") // 사무소 위치 정보 목록 조회
    public ResponseEntity<List<OfficeGeographyDTO>> getOfficeGeoList(){
        return ResponseEntity.ok().body(service.getOfficeGeographyList());
    }

    @GetMapping("/{userToken}/favorite") // 사무소 북마크 목록 조회
    public ResponseEntity<List<AgentBookmarkDTO>> getAgentBookmark(@PathVariable String userToken){
        return ResponseEntity.ok().body(service.getAgentBookmark(jwtProcessor.getUserId(userToken)));
    }

    @GetMapping("/{userToken}/isFavorite/{officeId}") // 북마크 여부 조회
    public ResponseEntity<Boolean> IsFavorite(@PathVariable String userToken, @PathVariable Long officeId){
        return ResponseEntity.ok().body(service.IsFavorite(jwtProcessor.getUserId(userToken), officeId));
    }

    @GetMapping("/{userToken}/favorite/{officeId}") // 북마크 추가
    public ResponseEntity<?> saveFavorite(@PathVariable String userToken, @PathVariable Long officeId){
        return ResponseEntity.ok().body(service.saveAgentBookmark(jwtProcessor.getUserId(userToken), officeId));
    }

    @DeleteMapping("/{userToken}/favorite/{officeId}") // 북마크 삭제
    public void deleteFavorite(@PathVariable String userToken, @PathVariable Long officeId){
        service.deleteAgentBookmark(jwtProcessor.getUserId(userToken), officeId);
    }
}
