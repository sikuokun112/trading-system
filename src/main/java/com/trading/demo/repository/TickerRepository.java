package com.trading.demo.repository;

import com.trading.demo.entity.TickerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerRepository extends JpaRepository<TickerEntity, Integer> {
}
