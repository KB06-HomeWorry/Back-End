package org.scoula.pricetrend.mapper;

import org.scoula.pricetrend.domain.PriceTrendVO;
import org.scoula.pricetrend.dto.MaxPriceDTO;

import java.util.List;

public interface PriceTrendMapper {

    List<PriceTrendVO> getList();
    List<MaxPriceDTO> getMaximumValue();

    PriceTrendVO get(int no);

//    int create(BoardVO board);
//
//    int update(BoardVO board);

    int delete(int no);
}
