package com.trading.demo.dto.ticker;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class HuobiTickerDTO implements TickerDTO<HuobiTickerDTO>{

    private String symbol;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double amount;
    private Double vol;
    private Integer count;
    private Double bid;
    private Double bidSize;
    private Double ask;
    private Double askSize;



    @Override
    public HuobiTickerDTO getTicker(List<HuobiTickerDTO> list, String symbol) {
        Optional<HuobiTickerDTO> matchingObject = list.stream().
                filter(item -> StringUtils.isNotBlank(item.getSymbol()) && item.getSymbol().toLowerCase().equals(symbol.toLowerCase())).
                findFirst();
        return matchingObject.get();
    }
}
