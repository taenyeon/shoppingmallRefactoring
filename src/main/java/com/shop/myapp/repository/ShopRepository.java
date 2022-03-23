package com.shop.myapp.repository;

import com.shop.myapp.dto.Shop;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface ShopRepository {
    Optional<Shop> findByShopName(String shopName);
    int insertShop(Shop shop);
    int updateByShopName(Shop shop);
    int deleteByMemberId(String memberId);
}
