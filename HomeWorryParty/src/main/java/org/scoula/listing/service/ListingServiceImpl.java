package org.scoula.listing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.agent.domain.AgentDetailVO;
import org.scoula.agent.dto.AgentDetailDTO;
import org.scoula.listing.domain.ListingVO;
import org.scoula.listing.mapper.ListingMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {
    private final ListingMapper mapper;

    @Override // 매물 목록 조회
    public List<ListingVO> getList() {
        return mapper.getList();
    }

    @Override // 매물 단건 조회
    public ListingVO get(int id) {
        return mapper.get(id);
    }

    @Override // 매물 삭제
    public void delete(int id) {
        mapper.delete(id);
    }

    @Override // 매물 북마크 등록
    public int saveFavorite(int id, long userId) {
        return mapper.saveFavorite(id, userId);
    }

    @Override // 매물 북마크 해제
    public int deleteFavorite(int id, long userId) {
        return mapper.deleteFavorite(id, userId);
    }

    @Override // 북마크 여부 조회
    public boolean isFavorite(int id, long userId) {
        int lid = mapper.isFavorite(id, userId);
        return lid != 0;
    }

    @Override // 북마크한 매물 목록 조회
    public List<ListingVO> getFavoriteList(long userId) {
        return mapper.getFavoriteList(userId);
    }

    @Override // 매물로 중개사 조회
    public AgentDetailDTO getAgency(int listingId) {
        List<AgentDetailVO> list = mapper.getAgency(listingId);

        if (list.isEmpty()) {
            return null;
        } else {
            return AgentDetailDTO.of(list.get(0));
        }
    }

    @Override // 중개사별 매물 목록 조회
    public List<ListingVO> getAgencyList(long officeId) {
        return mapper.getAgencyList(officeId);
    }
}
