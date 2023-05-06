package com.trading.demo.repository;

import com.trading.demo.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerRepository extends JpaRepository<Ticker, Integer> {
}
