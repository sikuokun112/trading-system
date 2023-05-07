package com.trading.demo.controller;

import com.trading.demo.common.ResponseApi;
import com.trading.demo.entity.TickerEntity;
import com.trading.demo.entity.UserEntity;
import com.trading.demo.entity.WalletEntity;
import com.trading.demo.request.CreateUserRequest;
import com.trading.demo.service.TradingService;
import com.trading.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseApi<UserEntity> createUser(@RequestBody CreateUserRequest request) {
        UserEntity newUser = userService.createNewUser(request.getName(), request.getPassword());
        return new ResponseApi<>(newUser, HttpStatus.OK);
    }

    @GetMapping("/{userId}/wallet")
    public ResponseApi<WalletEntity> checkWalletUser(@PathVariable Long userId) {
        WalletEntity walletEntity = userService.checkWalletUser(userId);
        return new ResponseApi<>(walletEntity, HttpStatus.OK);
    }
}
