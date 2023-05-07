package com.trading.demo.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class TradeCryptoRequest {
    @NonNull
    private Long userId;

    @NonNull
    private Double amount;

    @NonNull
    private String crypto;

    @NonNull
    private String action;

}
