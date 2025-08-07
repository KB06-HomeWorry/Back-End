package org.scoula.pricetrend.service;

import lombok.RequiredArgsConstructor;
import org.scoula.pricetrend.domain.PriceTrendVO;
import org.scoula.pricetrend.dto.MaxPriceDTO;
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

    @Override
    public List<MaxPriceDTO> getMaximumValue() {
        List<MaxPriceDTO> maxPrices = mapper.getMaximumValue();
        for (MaxPriceDTO dto : maxPrices) {
            long price = dto.getMaxPrice();
            double priceInEok = price / 100000000.0;
            dto.setFormattedPrice(String.format("%.1f억", priceInEok));
        }
        return maxPrices;
    }

    @Override
    public PriceTrendVO delete(int no) {
        PriceTrendVO board = get(no);
        mapper.delete(no);
        return board;
    }

}
