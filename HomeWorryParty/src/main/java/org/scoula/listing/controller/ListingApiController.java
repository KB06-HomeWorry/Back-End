package org.scoula.listing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.listing.domain.ListingVO;
import org.scoula.listing.service.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/listing")
@RequiredArgsConstructor
@Slf4j
public class ListingApiController {

    private final ListingService service;
    @GetMapping("")
    public List<ListingVO> getList() {
        return service.getList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ListingVO> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }
}
