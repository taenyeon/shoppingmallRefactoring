package com.shop.myapp.repository;

import com.shop.myapp.dto.Item;
import com.shop.myapp.dto.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemRepository {
    List<Item> findAll(Pagination pagination);
    List<Item> findAllBySearch(@Param("search") String search,@Param("pagination") Pagination pagination);
    List<Item> findAllByMemberId(@Param("memberId") String memberId, @Param("pagination") Pagination pagination,@Param("search") String search);
    List<Item> findNewItems();
    List<Item> findHitItems();
    List<Item> findBuyItems();
    Optional<Item> findByItemCode(String itemCode);
    int insertItem(Item item);
    int deleteItem(String itemCode);
    int updateItem(Item item);
    int getItemListCnt(String search);
    int getItemListCntByMemberId(String memberId);
    int itemHitUpWhenInItemDetail(String itemCode);

}
