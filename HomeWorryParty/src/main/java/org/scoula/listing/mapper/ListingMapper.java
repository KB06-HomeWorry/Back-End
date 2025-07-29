package org.scoula.listing.mapper;

import org.scoula.listing.domain.ListingVO;

import java.util.List;

public interface ListingMapper {

    List<ListingVO> getList();

    ListingVO  get(int no);

//    int create(BoardVO board);
//
//    int update(BoardVO board);

    int delete(int no);
}
