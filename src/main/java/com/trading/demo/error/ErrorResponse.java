package com.trading.demo.error;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String result;
    private long timestamp;
    private String errorMessage;
}
