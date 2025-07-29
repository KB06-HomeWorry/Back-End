package org.scoula.pricetrend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.pricetrend.domain.PriceTrendVO;
import org.scoula.pricetrend.service.PriceTrendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pricetrend")
@RequiredArgsConstructor
@Slf4j
public class PriceTrendApiController {

    private final PriceTrendService service;
    @GetMapping("")
    public List<PriceTrendVO> getList() {
        return service.getList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<PriceTrendVO> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }
}
