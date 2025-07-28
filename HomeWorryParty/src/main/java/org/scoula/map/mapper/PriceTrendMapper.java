package org.scoula.map.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.scoula.map.dto.PriceTrendDetailDTO;
import org.scoula.map.dto.PriceTrendMapDTO;

import java.util.List;

@MapperScan("org.scoula.map.mapper")
public interface PriceTrendMapper {
    //1. 지도에서 마커 표시하기 위해 가져오는 가격 위도 경도 정보
    List<PriceTrendMapDTO> selectPriceTrendsForMap();

    // 2. 특정 실거래가 상세 정보 조회
    PriceTrendDetailDTO selectPriceTrendDetail(@Param("id") Long id);
}
