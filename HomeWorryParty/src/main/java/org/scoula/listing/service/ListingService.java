package org.scoula.listing.service;

import org.scoula.listing.domain.ListingVO;

import java.util.List;

public interface ListingService {
    List<ListingVO> getList(); // 매물 목록 조회

    ListingVO get(int id); // 매물 단건 조회

    void delete(int id); // 매물 삭제
    
    int saveFavorite(int id, long userId); // 매물 북마크 등록
    
    int deleteFavorite(int id, long userId); // 매물 북마크 해제

    boolean isFavorite(int id, long userId); // 북마크 여부 조회
    
    List<ListingVO> getFavoriteList(long userId); // 북마크한 매물 목록 조회
}
