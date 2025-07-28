package org.scoula.map.service;

import org.scoula.map.dto.PriceTrendDetailDTO;
import org.scoula.map.dto.PriceTrendMapDTO;
import org.scoula.map.mapper.PriceTrendMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceTrendServiceImpl implements PriceTrendService {

    private final PriceTrendMapper priceTrendMapper;

    public PriceTrendServiceImpl(PriceTrendMapper priceTrendMapper) {
        this.priceTrendMapper = priceTrendMapper;
    }

    @Override
    public List<PriceTrendMapDTO> getPriceTrendsForMap() {
        return priceTrendMapper.getPriceTrendsForMap();
    }

    @Override
    public PriceTrendDetailDTO getPriceTrendDetail(Long id) {
        return priceTrendMapper.getPriceTrendDetail(id);
    }
}