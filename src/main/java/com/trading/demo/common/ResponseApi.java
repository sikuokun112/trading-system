package com.trading.demo.common;

import com.trading.demo.error.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

@Log4j2
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

    public ResponseApi<Object> handleException(String error, HttpStatus status) {
        log.error("Exception with error {}",error);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setResult("ERROR");
        errorResponse.setStatus(status.value());
        errorResponse.setTimestamp(System.currentTimeMillis());
        errorResponse.setErrorMessage(error);
        return new ResponseApi<>(errorResponse, status);
    }
}
