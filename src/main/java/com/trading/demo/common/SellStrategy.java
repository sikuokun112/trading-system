package com.trading.demo.common;

import com.trading.demo.entity.AssetEntity;
import com.trading.demo.entity.TickerEntity;
import com.trading.demo.entity.UserEntity;
import com.trading.demo.entity.WalletEntity;

import java.util.Date;
import java.util.Objects;

public class SellStrategy extends TradeAction implements TradeStrategy{

    public SellStrategy(String nameAction, UserEntity userEntity, TickerEntity tickerEntity, AssetEntity assetEntity, String crypto, Double amount) {
        super(nameAction, userEntity, tickerEntity,assetEntity, crypto, amount);
    }

    @Override
    public Double calculateBalanceAfterTrans() {
        return  this.getUserEntity().getWalletEntity().getBalance() + this.getAmount() * this.getTickerEntity().getSellPrice();
    }

    @Override
    public boolean checkAvailableTrans() {
        if (Objects.nonNull(this.getAssetEntity()) && this.getAssetEntity().getAmount() - this.getAmount() >=0) {
            return true;
        }
        return  false;
    }

    @Override
    public WalletEntity updateListAssetAmountInWallet() {
        Date curr = new Date();
        if (Objects.nonNull(this.getAssetEntity())) {
            this.getAssetEntity().setAmount(this.getAssetEntity().getAmount() - amount);
            this.getAssetEntity().setUpdatedAt(curr);
            this.getAssetEntity().setWalletEntity(this.getUserEntity().getWalletEntity());
        }
        return this.getUserEntity().getWalletEntity();
    }

    @Override
    public Double getPriceAction() {
        return this.getTickerEntity().getSellPrice();
    }

    @Override
    public String getPlatformAction() {
        return this.getTickerEntity().getSellPlatform();
    }

}
