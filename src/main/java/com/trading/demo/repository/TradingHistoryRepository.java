package com.trading.demo.repository;

import com.trading.demo.entity.TradingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradingHistoryRepository extends JpaRepository<TradingHistoryEntity, Long> {
    List<TradingHistoryEntity> findAllByUserId(Long userId);
}
