package org.scoula.pricetrend.service;

import org.scoula.pricetrend.domain.PriceTrendVO;

import java.util.List;

public interface PriceTrendService {
    List<PriceTrendVO> getList();

    PriceTrendVO get(int no);

//    void create(BoardVO board);
//
//    BoardVO update(BoardVO board);

    PriceTrendVO delete(int no);
}
