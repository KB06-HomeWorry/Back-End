package org.scoula.agent.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.agent.dto.AgentDetailDTO;
import org.scoula.agent.service.AgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequestMapping("/api/agent")
@RestController
@RequiredArgsConstructor
public class AgentController {
    private final AgentService service;

    @GetMapping("/fetch")
    public ResponseEntity<String> fetch(){
        return ResponseEntity.ok().body(service.fetchAndSaveOffice());
    }

    @GetMapping("/{officeId}")
    public ResponseEntity<AgentDetailDTO> getAgentDetail(@PathVariable Long officeId){
        return ResponseEntity.ok().body(service.getAgentDetail(officeId));
    }
}
