package org.scoula.sectionGeo.mapper;

import org.apache.ibatis.annotations.Param;
import org.scoula.sectionGeo.dto.SectionGeoDTO;

import java.util.List;

public interface SectionGeoMapper {
    List<String> getDepth1(); // 1단계 행정구역 목록 조회

    List<String> getDepth2(@Param("depth1") String depth1); // 2단계 행정구역 목록 조회

    List<String> getDepth3(@Param("depth1") String depth1, @Param("depth2") String depth2); // 3단계 행정구역 목록 조회

    SectionGeoDTO getSectionGeoData(@Param("depth1") String depth1, @Param("depth2") String depth2, @Param("depth3") String depth3); // 동 상세정보 조회
}
