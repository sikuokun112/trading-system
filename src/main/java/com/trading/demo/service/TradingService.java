package com.trading.demo.service;

import com.trading.demo.configuration.AppProperties;
import com.trading.demo.dto.ticker.*;
import com.trading.demo.entity.Ticker;
import com.trading.demo.repository.TickerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.trading.demo.utils.Constants.*;

@Log4j2
@Service
public class TradingService {

    private  ConcurrentHashMap<String, Double> buyMap = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<String, Double> sellMap = new ConcurrentHashMap<>();

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private TickerRepository tickerRepository;

    @Scheduled(fixedDelayString = "10000")
    public void storePricing() {
        saveBestPricing();
    }

    private void saveBestPricing() {
        List<String> cryptos = Arrays.asList(ETH_USDT, BTC_USDT);
        List<BinanceTickerDTO> binanceTickerDTOList = getBinanceTicker();
        List<HuobiTickerDTO> huobiTickerDTOList = getHuobiTicker();

        List<Ticker> listTicker = new ArrayList<>();


            for (String crypto : cryptos) {
                BinanceTickerDTO binanceTicker = (BinanceTickerDTO) TickerFactory.getTickerTradingSystem(BINANCE).getTicker(binanceTickerDTOList, crypto);
                HuobiTickerDTO huobiTicker = (HuobiTickerDTO) TickerFactory.getTickerTradingSystem(HUOBI).getTicker(huobiTickerDTOList, crypto);
                double binanceAsk = binanceTicker.getAskPrice();
                double huobiAsk = huobiTicker.getAsk();
                double buyPrice = binanceAsk < huobiAsk ? binanceAsk : huobiAsk;
                String buyPlatform = binanceAsk < huobiAsk ? BINANCE : HUOBI;

                double binanceBid = binanceTicker.getBidPrice();
                double huobiBid = huobiTicker.getBid();
                double sellPrice = binanceBid > huobiBid ? binanceBid : huobiBid;
                String sellPlatform = binanceBid > huobiBid ? BINANCE : HUOBI;
                if (!buyMap.containsKey(crypto)) {
                    buyMap.put(crypto, buyPrice);
                }
                buyMap.computeIfPresent(crypto, (key, oldValue) -> buyPrice);

                if (!sellMap.containsKey(crypto)) {
                    sellMap.put(crypto, sellPrice);
                }
                sellMap.computeIfPresent(crypto, (key, oldValue) -> sellPrice);


                System.out.println(crypto);
                System.out.println(buyPrice+"_"+sellPrice+"|"+buyPlatform+"_"+sellPlatform);
                System.out.println("BUY MAP: " + buyMap.get(crypto));
                System.out.println("SELL MAP: " + sellMap.get(crypto));

//                // save best pricing to db
                Ticker ticker = new Ticker();
                ticker.setPair(crypto);
                ticker.setBuyPrice(buyMap.get(crypto));
                ticker.setSellPrice(sellMap.get(crypto));
                ticker.setBuyPlatform(buyPlatform);
                ticker.setSellPlatform(sellPlatform);
                ticker.setCreatedAt(new Date());
                listTicker.add(ticker);
            }

        tickerRepository.saveAll(listTicker);


    }

    public List<BinanceTickerDTO> getBinanceTicker() {
        List<BinanceTickerDTO> bookTickerDTOS = restTemplateService.getRestForList(appProperties.getBinanceUrl(), null, BinanceTickerDTO.class);
        return bookTickerDTOS;
    }

    public List<HuobiTickerDTO> getHuobiTicker() {
        HuobiDTO response = (HuobiDTO) restTemplateService.getRestForObject(appProperties.getHuobiUrl(), null, HuobiDTO.class);
        return response.getData();
    }

}
