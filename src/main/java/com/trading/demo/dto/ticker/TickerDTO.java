package com.trading.demo.dto.ticker;

import java.util.List;

public interface TickerDTO<T> {
     T getTicker(List<T> list, String symbol);
}
