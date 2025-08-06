package org.scoula.listing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.listing.domain.ListingVO;
import org.scoula.listing.service.ListingService;
import org.scoula.security.util.JwtProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}/favorite/{userToken}") // 매물 북마크 등록
    public ResponseEntity<?> saveFavorite(@PathVariable int id, @PathVariable String userToken) {
        return ResponseEntity.ok().body(service.saveFavorite(id, jwtProcessor.getUserId(userToken)));
    }

    @DeleteMapping("/{id}/disFavorite/{userToken}") // 매물 북마크 해제
    public ResponseEntity<?> deleteFavorite(@PathVariable int id, @PathVariable String userToken) {
        return ResponseEntity.ok().body(service.deleteFavorite(id, jwtProcessor.getUserId(userToken)));
    }

    @GetMapping("/{id}/isFavorite/{userToken}") // 북마크 여부 조회
    public ResponseEntity<Boolean> isFavorite(@PathVariable int id, @PathVariable String userToken) {
        return ResponseEntity.ok().body(service.isFavorite(id, jwtProcessor.getUserId(userToken)));
    }

    @GetMapping("/favorite/{userToken}") // 북마크한 매물 목록 조회
    public ResponseEntity<List<ListingVO>> getFavoriteList(@PathVariable String userToken) {
        return ResponseEntity.ok().body(service.getFavoriteList(jwtProcessor.getUserId(userToken)));
    }
}
