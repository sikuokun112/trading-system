package com.trading.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private String result;
    private Integer httpStatus;
    private T data;
    private static final String SUCCESS = "Success";

    public Response<T> of(T data, String message, Integer status) {
        Response<T> response = new Response<>();
        response.setResult(message);
        response.setData(data);
        response.setHttpStatus(status);
        return response;
    }
}
