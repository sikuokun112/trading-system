package com.trading.demo.repository;

import com.trading.demo.entity.AssetEntity;
import com.trading.demo.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<AssetEntity, String> {
    AssetEntity findAssetEntityByCryptoIdAndWalletEntity(String cryptoId, WalletEntity walletEntity);
}
