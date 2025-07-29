package org.scoula.pricetrend.service;

import lombok.RequiredArgsConstructor;
import org.scoula.pricetrend.domain.PriceTrendVO;
import org.scoula.pricetrend.mapper.PriceTrendMapper;
import org.springframework.stereotype.Service;

import java.util.List;

//@Log4j
@Service
@RequiredArgsConstructor
public class PriceTrendServiceImpl implements PriceTrendService {
    private final PriceTrendMapper mapper;

    @Override
    public List<PriceTrendVO> getList() {
        return mapper.getList();
    }

    @Override
    public PriceTrendVO get(int no) {
        return mapper.get(no);
    }

//    @Override
//    public void create(BoardVO board) {
//        mapper.create(board);
//    }
//
//    @Override
//    public BoardVO update(BoardVO board) {
//        mapper.update(board);
//        return get(board.getNo());
//    }

    @Override
    public PriceTrendVO delete(int no) {
        PriceTrendVO board = get(no);
        mapper.delete(no);
        return board;
    }

}
