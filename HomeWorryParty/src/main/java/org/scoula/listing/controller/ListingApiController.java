package org.scoula.listing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.agent.dto.AgentDetailDTO;
import org.scoula.listing.domain.ListingVO;
import org.scoula.listing.service.ListingService;
import org.scoula.security.util.JwtProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/listing")
@RequiredArgsConstructor
@Slf4j
public class ListingApiController {
    private final ListingService service;
    private final JwtProcessor jwtProcessor;

    @GetMapping("") // 매물 목록 조회
    public List<ListingVO> getList() {
        return service.getList();
    }

    @GetMapping("/{id}") // 매물 단건 조회
    public ResponseEntity<ListingVO> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/{id}/favorite") // 매물 북마크 등록
    public ResponseEntity<?> saveFavorite(@PathVariable int id, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            return ResponseEntity.ok().body(service.saveFavorite(id, jwtProcessor.getUserId(jwt)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/disFavorite") // 매물 북마크 해제
    public ResponseEntity<?> deleteFavorite(@PathVariable int id, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            return ResponseEntity.ok().body(service.deleteFavorite(id, jwtProcessor.getUserId(jwt)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/isFavorite") // 북마크 여부 조회
    public ResponseEntity<Boolean> isFavorite(@PathVariable int id, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            return ResponseEntity.ok().body(service.isFavorite(id, jwtProcessor.getUserId(jwt)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/favorite") // 북마크한 매물 목록 조회
    public ResponseEntity<List<ListingVO>> getFavoriteList(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            return ResponseEntity.ok().body(service.getFavoriteList(jwtProcessor.getUserId(jwt)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAgency/{listingId}") // 매물별 중개사 정보 조회
    public ResponseEntity<AgentDetailDTO> getAgency(@PathVariable int listingId) {
        return ResponseEntity.ok().body(service.getAgency(listingId));
    }

    @GetMapping("/getAgencyList/{officeId}") // 중개사별 매물 목록 조회
    public ResponseEntity<List<ListingVO>> getAgencyList(@PathVariable long officeId) {
        return ResponseEntity.ok().body(service.getAgencyList(officeId));
    }
}
