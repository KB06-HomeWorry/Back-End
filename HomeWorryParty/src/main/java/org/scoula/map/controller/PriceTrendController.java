package org.scoula.map.controller;

import org.scoula.map.dto.PriceTrendDetailDTO;
import org.scoula.map.dto.PriceTrendMapDTO;
import org.scoula.map.service.PriceTrendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricetrend")
@CrossOrigin(origins="http://localhost:5173")
public class PriceTrendController {
    private final PriceTrendService priceTrendService;

    public PriceTrendController(PriceTrendService priceTrendService) {
        this.priceTrendService = priceTrendService;
    }

    @GetMapping("/map")
    public ResponseEntity<List<PriceTrendMapDTO>> getPriceTrendsForMap() {
        List<PriceTrendMapDTO> trends = priceTrendService.getPriceTrendsForMap();
        System.out.println("🔥 프론트에 보낼 데이터: " + trends);
        return ResponseEntity.ok(trends);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceTrendDetailDTO> getPriceTrendDetail(@PathVariable Long id) {
        PriceTrendDetailDTO detail = priceTrendService.getPriceTrendDetail(id);
        if (detail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detail);
    }
}
