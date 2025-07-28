package org.scoula.map.service;

import org.mapstruct.Mapper;
import org.scoula.map.dto.PriceTrendDetailDTO;
import org.scoula.map.dto.PriceTrendMapDTO;

import java.util.List;

@Mapper
public interface PriceTrendService {
    List<PriceTrendMapDTO> getPriceTrendsForMap();
    PriceTrendDetailDTO getPriceTrendDetail(Long id);
}
