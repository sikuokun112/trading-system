package com.trading.demo.repository;

import com.trading.demo.entity.TickerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends JpaRepository<TickerEntity, Long> {

}
