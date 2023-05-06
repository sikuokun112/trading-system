package com.trading.demo.configuration;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class AppProperties {

    @Value("${binance.api.url}")
    private String binanceUrl;

    @Value("${huobi.api.url}")
    private String huobiUrl;
}
