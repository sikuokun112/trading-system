package com.trading.demo.service;

import com.trading.demo.configuration.AppProperties;
import com.trading.demo.dto.ticker.*;
import com.trading.demo.entity.TickerEntity;
import com.trading.demo.repository.TickerRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
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

        List<TickerEntity> listTickerEntity = new ArrayList<>();


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
                TickerEntity tickerEntity = new TickerEntity();
                tickerEntity.setPair(crypto);
                tickerEntity.setBuyPrice(buyMap.get(crypto));
                tickerEntity.setSellPrice(sellMap.get(crypto));
                tickerEntity.setBuyPlatform(buyPlatform);
                tickerEntity.setSellPlatform(sellPlatform);
                tickerEntity.setCreatedAt(new Date());
                listTickerEntity.add(tickerEntity);
            }

        tickerRepository.saveAll(listTickerEntity);


    }

    public List<BinanceTickerDTO> getBinanceTicker() {
        List<BinanceTickerDTO> bookTickerDTOS = restTemplateService.getRestForList(appProperties.getBinanceUrl(), null, BinanceTickerDTO.class);
        return bookTickerDTOS;
    }

    public List<HuobiTickerDTO> getHuobiTicker() {
        HuobiDTO response = (HuobiDTO) restTemplateService.getRestForObject(appProperties.getHuobiUrl(), null, HuobiDTO.class);
        return response.getData();
    }

    public TickerEntity getBestPriceByCrypto(String crypto) {
        Optional<TickerEntity> latestBtcTicker = tickerRepository.findAll().stream().filter(c -> StringUtils.isNotBlank(c.getPair()) && c.getPair().toLowerCase().equals(crypto))
                .sorted(Comparator.comparing(TickerEntity::getId).reversed())
                .findFirst();

        return latestBtcTicker.get();
    }

    public List<TickerEntity> getLatestBestPricing() {
        List<TickerEntity> tickerList = new ArrayList<>();

        Optional<TickerEntity> latestBtcTicker = tickerRepository.findAll().stream().filter(c -> StringUtils.isNotBlank(c.getPair()) && c.getPair().toLowerCase().equals(BTC_USDT.toLowerCase()))
                .sorted(Comparator.comparing(TickerEntity::getId).reversed())
                .findFirst();

        Optional<TickerEntity> latestEthTicker = tickerRepository.findAll().stream().filter(c -> StringUtils.isNotBlank(c.getPair()) && c.getPair().toLowerCase().equals(ETH_USDT.toLowerCase()))
                .sorted(Comparator.comparing(TickerEntity::getCreatedAt).reversed())
                .findFirst();

        if (latestBtcTicker.isPresent()) {
            tickerList.add(latestBtcTicker.get());
        }

        if (latestEthTicker.isPresent()) {
            tickerList.add(latestEthTicker.get());
        }

        return tickerList;
    }

}
