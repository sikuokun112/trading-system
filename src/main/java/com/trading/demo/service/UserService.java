package com.trading.demo.service;

import com.trading.demo.entity.TradingHistoryEntity;
import com.trading.demo.entity.UserEntity;
import com.trading.demo.entity.WalletEntity;
import com.trading.demo.repository.TradingHistoryRepository;
import com.trading.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TradingHistoryRepository tradingHistoryRepository;

    public UserEntity createNewUser(String userName, String password) {
        Date current = new Date();
        UserEntity oldUser = userRepository.findUserEntityByUserName(userName);
        if (!Objects.isNull(oldUser)) {
            throw new RuntimeException("Username is already existed! Please try another");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        userEntity.setCreatedAt(current);
        userRepository.save(userEntity);
        return userRepository.findUserEntityByUserName(userName);
    }

    public WalletEntity checkWalletUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (Objects.isNull(user)) {
            throw new RuntimeException("This user is not exist");
        }
        return user.getWalletEntity();
    }

    public List<TradingHistoryEntity> getAllTradingHistoryUser(Long userId) {
        return tradingHistoryRepository.findAllByUserId(userId);
    }
}
