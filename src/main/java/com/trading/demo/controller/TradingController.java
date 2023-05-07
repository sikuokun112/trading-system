package com.trading.demo.controller;


import com.trading.demo.common.ResponseApi;
import com.trading.demo.entity.TickerEntity;
import com.trading.demo.entity.WalletEntity;
import com.trading.demo.request.TradeCryptoRequest;
import com.trading.demo.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trading")
public class TradingController {

    @Autowired
    private TradingService tradingService;

    @GetMapping("/aggregated-price/latest")
    public ResponseApi<List<TickerEntity>> getLatestBestPricing() {
        List<TickerEntity> tickerList = tradingService.getLatestBestPricing();
        return new ResponseApi<>(tickerList, HttpStatus.OK);
    }

    @GetMapping("/price/{crypto}")
    public ResponseApi<TickerEntity> getBestPricingByCrypto(@PathVariable String crypto) {
        TickerEntity ticker = tradingService.getBestPriceByCrypto(crypto);
        return new ResponseApi<>(ticker, HttpStatus.OK);
    }

    @PostMapping("/trade-crypto")
    public ResponseApi<WalletEntity> buyAction(@RequestBody TradeCryptoRequest request) {
        WalletEntity walletEntity = tradingService.tradeAction(request.getUserId(), request.getCrypto(), request.getAmount(), request.getAction());
        return new ResponseApi<>(walletEntity, HttpStatus.OK);
    }

}
