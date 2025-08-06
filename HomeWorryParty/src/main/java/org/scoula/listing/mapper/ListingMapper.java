package org.scoula.listing.mapper;

import org.apache.ibatis.annotations.Param;
import org.scoula.listing.domain.ListingVO;

import java.util.List;

public interface ListingMapper {

    List<ListingVO> getList(); // 매물 목록 조회

    ListingVO  get(int no); // 매물 단건 조회

    int delete(int no); // 매물 삭제
    
    int saveFavorite(@Param("id") int id, @Param("userId") long userId); // 매물 북마크 등록
    
    int deleteFavorite(@Param("id") int id, @Param("userId") long userId); // 매물 북마크 해제

    int isFavorite(@Param("id") int id, @Param("userId") long userId); // 북마크 여부 조회
    
    List<ListingVO> getFavoriteList(@Param("userId") long userId); // 북마크한 매물 목록 조회
}
