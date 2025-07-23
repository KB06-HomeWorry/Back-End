package org.scoula.documentAnalysis.mapper;

import org.mapstruct.Mapper;
import org.scoula.documentAnalysis.dto.IllegalBuildingCheckDTO;

import java.util.List;

@Mapper
public interface IllegalBuildingCheckMapper {

    // 단건 저장
    int insert(IllegalBuildingCheckDTO dto);

    // 단건 조회
    IllegalBuildingCheckDTO findById(Long id);

    // 전체 목록 조회
    List<IllegalBuildingCheckDTO> findAll();

    // (선택) 조건 검색 등 다양한 쿼리도 필요시 추가 가능
}