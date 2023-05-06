package com.trading.demo.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "TRADING_TICKER")
@NoArgsConstructor
public class Ticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "buyPrice")
    private Double buyPrice;

    @Column(name = "sellPrice")
    private Double sellPrice;

    @Column(name = "buyPlatform")
    private String buyPlatform;

    @Column(name = "sellPlatform")
    private String sellPlatform;

    @Column(name = "pair")
    private String pair;
}
