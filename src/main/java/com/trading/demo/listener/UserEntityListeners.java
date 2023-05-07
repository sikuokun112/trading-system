package com.trading.demo.listener;

import com.trading.demo.entity.UserEntity;
import com.trading.demo.entity.WalletEntity;
import com.trading.demo.repository.AssetRepository;
import com.trading.demo.repository.UserRepository;
import com.trading.demo.repository.WalletRepository;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Component
public class UserEntityListeners {

    /**
     * Pre persist
     */
    @PrePersist
    public void prePersist(UserEntity user) {
        // create new wallet & init money
        Date currentTime = new Date();
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(UUID.randomUUID().toString());
        walletEntity.setBalance(Double.valueOf(50000));
        walletEntity.setCurrency("USDT");
        walletEntity.setCreatedAt(currentTime);
        walletEntity.setUpdatedAt(currentTime);
        walletEntity.setUser(user);

        user.setWalletEntity(walletEntity);

    }

    private static AssetRepository assetRepository;
    private static WalletRepository walletRepository;
    private static UserRepository userRepository;

    @Autowired
    public void init(UserRepository userRepository, WalletRepository walletRepository, AssetRepository assetRepository) {
        UserEntityListeners.userRepository = userRepository;
        UserEntityListeners.walletRepository = walletRepository;
        UserEntityListeners.assetRepository = assetRepository;

    }



}
