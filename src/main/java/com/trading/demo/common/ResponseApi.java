package com.trading.demo.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ResponseApi<T> extends ResponseEntity<T> {
    public ResponseApi(HttpStatus status) {
        super(status);
    }

    public ResponseApi(T body, HttpStatus status) {
        super(body, status);
    }

    public ResponseApi(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseApi(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public ResponseApi(T body, MultiValueMap<String, String> headers, int rawStatus) {
        super(body, headers, rawStatus);
    }
}
