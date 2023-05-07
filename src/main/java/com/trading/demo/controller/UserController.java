package com.trading.demo.controller;

import com.trading.demo.common.Response;
import com.trading.demo.entity.TradingHistoryEntity;
import com.trading.demo.entity.UserEntity;
import com.trading.demo.entity.WalletEntity;
import com.trading.demo.request.CreateUserRequest;
import com.trading.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Response<UserEntity> createUser(@RequestBody CreateUserRequest request) {
        try {
            UserEntity newUser = userService.createNewUser(request.getName(), request.getPassword());
            return new Response().of(newUser, "Successfully created user ", HttpStatus.OK.value());
        } catch (Exception e) {
            return new Response().of(null, e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }

    }

    @GetMapping("/{userId}/wallet")
    public Response<WalletEntity> checkWalletUser(@PathVariable Long userId) {
        try {
            WalletEntity walletEntity = userService.checkWalletUser(userId);
            return new Response().of(walletEntity, "success", HttpStatus.OK.value());
        } catch (Exception e) {
            return new Response().of(null, e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    @GetMapping("/{userId}/history-trans")
    public Response<List<TradingHistoryEntity>> getAllHistoryTrans(@PathVariable Long userId) {
        try {
            List<TradingHistoryEntity> tradingHistories = userService.getAllTradingHistoryUser(userId);
            return new Response().of(tradingHistories, "success", HttpStatus.OK.value());
        } catch (Exception e) {
            return new Response().of(null, e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }
}
