package org.scoula.documentAnalysis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.documentAnalysis.dto.IllegalBuildingCheckDTO;
import org.scoula.documentAnalysis.mapper.IllegalBuildingCheckMapper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class DocumentAnalysisServiceImpl implements DocumentAnalysisService{
    private final IllegalBuildingCheckMapper illegalBuildingCheckMapper;

    public void insert(IllegalBuildingCheckDTO dto) {
        illegalBuildingCheckMapper.insert(dto);
    }
}
