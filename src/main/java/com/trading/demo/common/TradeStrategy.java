package com.trading.demo.common;

import com.trading.demo.entity.AssetEntity;
import com.trading.demo.entity.WalletEntity;

public interface TradeStrategy {
    public Double calculateBalanceAfterTrans();
    public boolean checkAvailableTrans();

    public WalletEntity updateListAssetAmountInWallet();

    public Double getPriceAction();

    public String getPlatformAction();

}
