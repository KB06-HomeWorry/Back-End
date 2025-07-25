package org.scoula.map.service;

import org.scoula.map.dto.PriceTrendDetailDTO;
import org.scoula.map.dto.PriceTrendMapDTO;

import java.util.List;

public interface PriceTrendService {
    List<PriceTrendMapDTO> getPriceTrendsForMap();
    PriceTrendDetailDTO getPriceTrendDetail(Long id);
}
