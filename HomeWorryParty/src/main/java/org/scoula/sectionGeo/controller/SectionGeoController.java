package org.scoula.sectionGeo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.sectionGeo.service.SectionGeoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequestMapping("/section")
@RestController
@RequiredArgsConstructor
public class SectionGeoController {
    private final SectionGeoService service;

    @GetMapping("/") // 1단계 행정구역 목록 조회 (시, 도)
    public ResponseEntity<List<String>> getDepth1(){
        return ResponseEntity.ok().body(service.getDepth1());
    }

    @GetMapping("/{depth1}") // 2단계 행정구역 목록 조회 (시, 군, 구)
    public ResponseEntity<List<String>> getDepth2(@PathVariable String depth1){
        return ResponseEntity.ok().body(service.getDepth2(depth1));
    }

    @GetMapping("/{depth1}/{depth2}") // 3단계 행정구역 목록 조회 (읍, 면, 동)
    public ResponseEntity<List<String>> getDepth3(@PathVariable String depth1, @PathVariable String depth2){
        return ResponseEntity.ok().body(service.getDepth3(depth1, depth2));
    }
}
