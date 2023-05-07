package com.trading.demo.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateUserRequest {
    @NonNull
    private String name;

    @NonNull
    private String password;
}
