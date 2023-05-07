package com.trading.demo.common;

import com.trading.demo.entity.AssetEntity;
import com.trading.demo.entity.TickerEntity;
import com.trading.demo.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeAction {
    protected String nameAction;
    protected UserEntity userEntity;
    protected TickerEntity tickerEntity;
    protected AssetEntity assetEntity;
    protected String crypto;
    protected Double amount;
}
