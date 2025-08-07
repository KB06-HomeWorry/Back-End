package org.scoula.pricetrend.service;

import org.scoula.pricetrend.domain.PriceTrendVO;
import org.scoula.pricetrend.dto.MaxPriceDTO;

import java.util.List;

public interface PriceTrendService {
    List<PriceTrendVO> getList();

    PriceTrendVO get(int no);

    List<MaxPriceDTO> getMaximumValue();

//    void create(BoardVO board);
//
//    BoardVO update(BoardVO board);

    PriceTrendVO delete(int no);
}
