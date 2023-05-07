package com.trading.demo.controller;


import com.trading.demo.common.Response;
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
    public Response<List<TickerEntity>> getLatestBestPricing() {
        try {
            List<TickerEntity> tickerList = tradingService.getLatestBestPricing();
            return new Response().of(tickerList, "Success", HttpStatus.OK.value());
        } catch (Exception e) {
            return new Response().of(null, e.getMessage(), HttpStatus.BAD_REQUEST.value());

        }
    }

    @GetMapping("/price/{crypto}")
    public Response<TickerEntity> getBestPricingByCrypto(@PathVariable String crypto) {
        try {
            TickerEntity ticker = tradingService.getBestPriceByCrypto(crypto);
            return new Response().of(ticker, "Success", HttpStatus.OK.value());
        } catch (Exception e) {
            return new Response().of(null, e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }

    }

    @PostMapping("/trade-crypto")
    public Response<WalletEntity> buyAction(@RequestBody TradeCryptoRequest request) {
        try {
            WalletEntity walletEntity = tradingService.tradeAction(request.getUserId(), request.getCrypto(), request.getAmount(), request.getAction());
            return new Response().of(walletEntity, "Success", HttpStatus.OK.value());
        } catch (Exception e) {
            return new Response().of(null, e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }

    }

}
