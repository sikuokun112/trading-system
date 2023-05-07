package com.trading.demo.controller;


import com.trading.demo.common.ResponseApi;
import com.trading.demo.entity.TickerEntity;
import com.trading.demo.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
