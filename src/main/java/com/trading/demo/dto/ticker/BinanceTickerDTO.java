package com.trading.demo.dto.ticker;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class BinanceTickerDTO implements TickerDTO<BinanceTickerDTO>{

    private String symbol;
    private Double bidPrice;
    private Double bidQty;
    private Double askPrice;
    private Double askQty;

    @Override
    public BinanceTickerDTO getTicker(List<BinanceTickerDTO> list, String symbol) {
        Optional<BinanceTickerDTO> matchingObject = list.stream().
                filter(item -> StringUtils.isNotBlank(item.getSymbol()) && item.getSymbol().toLowerCase().equals(symbol.toLowerCase())).
                findFirst();
        return matchingObject.get();
    }
}
