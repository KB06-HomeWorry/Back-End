package org.scoula.listing.service;

import org.scoula.listing.domain.ListingVO;

import java.util.List;

public interface ListingService {
    List<ListingVO> getList();

    ListingVO get(int no);

//    void create(BoardVO board);
//
//    BoardVO update(BoardVO board);

    ListingVO delete(int no);
}
