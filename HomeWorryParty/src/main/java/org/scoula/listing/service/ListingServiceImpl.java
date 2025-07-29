package org.scoula.listing.service;

import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j;
import org.scoula.listing.domain.ListingVO;
import org.scoula.listing.mapper.ListingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

//@Log4j
@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {
    private final ListingMapper mapper;

    @Override
    public List<ListingVO> getList() {
        return mapper.getList();
    }

    @Override
    public ListingVO get(int no) {
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
    public ListingVO delete(int no) {
        ListingVO board = get(no);
        mapper.delete(no);
        return board;
    }

}
