package org.scoula.checklist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.service.ChecklistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/checklist")
public class ChecklistController {

    final ChecklistService service;

    @GetMapping("")
    public ResponseEntity<List<ChecklistDTO>> getChecklist(String type,  String stage) {
        return ResponseEntity.ok().body(service.getChecklist(type, stage));
    }

}
