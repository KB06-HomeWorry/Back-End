package org.scoula.sectionGeo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.sectionGeo.dto.SectionGeoDTO;
import org.scoula.sectionGeo.mapper.SectionGeoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class SectionGeoService {
    private final SectionGeoMapper mapper;

    // SGIS API 키
    private static final String consumerKey = "0a38c79bda30482c9bb4";
    private static final String consumerSecret = "0e6f7465fcc046f384e1";

    // 1단계 행정구역 목록 조회
    public List<String> getDepth1(){
        return mapper.getDepth1();
    }

    // 2단계 행정구역 목록 조회
    public List<String> getDepth2(String depth1){
        return mapper.getDepth2(depth1);
    }

    // 3단계 행정구역 목록 조회
    public List<String> getDepth3(String depth1, String depth2){
        return mapper.getDepth3(depth1, depth2);
    }

    // 행정구역 상세정보 조회
    public SectionGeoDTO getSectionGeoData(String depth1, String depth2, String depth3){
        return mapper.getSectionGeoData(depth1, depth2, depth3);
    }
}
