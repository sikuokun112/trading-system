package com.trading.demo.dto.ticker;

import static com.trading.demo.utils.Constants.BINANCE;
import static com.trading.demo.utils.Constants.HUOBI;

public class TickerFactory {
    private TickerFactory() {}

    public static final TickerDTO getTickerTradingSystem(String system) {
        switch (system) {
            case BINANCE:
                return new BinanceTickerDTO();
            case HUOBI:
                return new HuobiTickerDTO();
            default:
                throw new IllegalCallerException("This type of trading system is currently not support!");
        }
    }
}
