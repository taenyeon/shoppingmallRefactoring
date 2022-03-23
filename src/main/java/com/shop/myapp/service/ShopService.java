package com.shop.myapp.service;

import com.shop.myapp.dto.Shop;
import com.shop.myapp.repository.ShopRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(SqlSession session) {
        this.shopRepository = session.getMapper(ShopRepository.class);
    }

    public int insertShop(Shop shop) {
        return shopRepository.insertShop(shop);
    }

    public Shop findByShopName(String shopName) {
        return shopRepository
                .findByShopName(shopName)
                .orElseThrow(() -> new IllegalStateException("상점을 찾을 수 없습니다."));
    }

    public int updateByShopName(Shop shop) {
        return shopRepository.updateByShopName(shop);
    }

    public int deleteByMemberId(String memberId) {
        return shopRepository.deleteByMemberId(memberId);
    }

}
