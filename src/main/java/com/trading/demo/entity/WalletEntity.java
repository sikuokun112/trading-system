package com.trading.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USER_WALLET")
@NoArgsConstructor
public class WalletEntity {

    @Id
    private String id;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "currency")
    private String currency;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_wallet__user"))
    @JsonIgnore
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "walletEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("assetEntityList")
    private List<AssetEntity> assetEntityList;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
