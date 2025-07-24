package org.scoula.documentAnalysis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DocumentAnalysisController {

    public void test(){

    }

}
