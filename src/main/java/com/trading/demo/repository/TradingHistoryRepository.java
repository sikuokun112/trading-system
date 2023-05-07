package com.trading.demo.repository;

import com.trading.demo.entity.TradingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingHistoryRepository extends JpaRepository<TradingHistoryEntity, Long> {
}
