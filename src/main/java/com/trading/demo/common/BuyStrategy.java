package com.trading.demo.common;


import com.trading.demo.entity.AssetEntity;
import com.trading.demo.entity.TickerEntity;
import com.trading.demo.entity.UserEntity;
import com.trading.demo.entity.WalletEntity;

import java.util.Date;
import java.util.Objects;

public class BuyStrategy extends TradeAction implements TradeStrategy{

    public BuyStrategy(String nameAction, UserEntity userEntity, TickerEntity tickerEntity, AssetEntity assetEntity, String crypto, Double amount) {
        super(nameAction, userEntity, tickerEntity,assetEntity, crypto, amount);
    }

    @Override
    public Double calculateBalanceAfterTrans() {
       return  this.getUserEntity().getWalletEntity().getBalance() - this.getAmount() * this.getTickerEntity().getBuyPrice();
    }

    @Override
    public boolean checkAvailableTrans() {
        if (this.getUserEntity().getWalletEntity().getBalance() > 0 && calculateBalanceAfterTrans() >=0) {
            return true;
        }
        return false;
    }

    @Override
    public WalletEntity updateListAssetAmountInWallet() {
        Date curr = new Date();
        if (Objects.nonNull(this.getAssetEntity())) {
            this.getAssetEntity().setAmount(this.getAssetEntity().getAmount() + amount);
            this.getAssetEntity().setUpdatedAt(curr);
            this.getAssetEntity().setWalletEntity(this.getUserEntity().getWalletEntity());
        } else {
            AssetEntity assetEntity = new AssetEntity();
            assetEntity.setCryptoId(crypto);
            assetEntity.setAmount(amount);
            assetEntity.setCreatedAt(curr);
            assetEntity.setUpdatedAt(curr);
            assetEntity.setWalletEntity(this.getUserEntity().getWalletEntity());
            this.getUserEntity().getWalletEntity().getAssetEntityList().add(assetEntity);
        }
        return this.getUserEntity().getWalletEntity();
    }

    @Override
    public Double getPriceAction() {
        return this.getTickerEntity().getBuyPrice();
    }

    @Override
    public String getPlatformAction() {
        return this.getTickerEntity().getBuyPlatform();
    }
}
