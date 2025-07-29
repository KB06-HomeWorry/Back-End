package org.scoula.documentAnalysis.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.scoula.documentAnalysis.domain.IllegalBuildingCheckVO;
import org.scoula.documentAnalysis.domain.MonthlyRentVO;

import java.util.List;

@Mapper
public interface DocumentAnalysisMapper {

    // 단건 저장
    int insert(IllegalBuildingCheckVO illegalBuildingCheckVO);

    // 단건 조회
    IllegalBuildingCheckVO findByAddress(String address);

    // 전체 목록 조회
    List<IllegalBuildingCheckVO> findAll();

    // 전세 평균 매물 가격 조회
    Long getWholeRent(@Param("address") String address,
                      @Param("minSize") Long minSize,
                      @Param("maxSize") Long maxSize);

    // 월세 평균 매물 가격 조회
    MonthlyRentVO getMonthRent(@Param("address") String address,
                               @Param("minSize") Long minSize,
                               @Param("maxSize") Long maxSize);

    // 매매 평균 매물 가격 조회
    Long getBuy(@Param("address") String address,
                @Param("minSize") Long minSize,
                @Param("maxSize") Long maxSize);

    // (선택) 조건 검색 등 다양한 쿼리도 필요시 추가 가능
}