package com.shop.myapp.service;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.myapp.dto.Item;
import com.shop.myapp.dto.MemberSession;
import com.shop.myapp.dto.Pagination;
import com.shop.myapp.repository.ItemRepository;

@Service
@Transactional(rollbackFor = {Exception.class})
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemOptionService itemOptionService;


    public ItemService(@Autowired SqlSession sqlSession, ItemOptionService itemOptionService) {
        this.itemRepository = sqlSession.getMapper(ItemRepository.class);
        this.itemOptionService = itemOptionService;

    }

    public Item getItem(String itemCode) {
        // itemCode 로 상품 조회
        itemRepository.itemHitUpWhenInItemDetail(itemCode);
        Optional<Item> item = itemRepository.findByItemCode(itemCode);
        // 상품 null 체크
        // 상품의 옵션 조회
        // 상품에 옵션 넣기
        return item.orElseThrow(() -> new IllegalStateException("Not Found Item"));
    }

    public List<Item> getItems(Pagination pagination) {
        return itemRepository.findAll(pagination);
    }

    public int createItem(Item item) {
        itemRepository.insertItem(item);
        return itemOptionService.insertItemOptions(item.getItemOptions(), item.getItemCode());
    }

    public int deleteItem(String itemCode) {
        itemOptionService.deleteByItemCode(itemCode);
        return itemRepository.deleteItem(itemCode);
    }

    public int updateItem(Item item) {
        itemOptionService.modifyItemOption(item.getItemOptions(),item.getItemCode());
        return itemRepository.updateItem(item);
    }

    public int getItemListCnt(String search) {
        return itemRepository.getItemListCnt(search);
    }

    public int getItemListCntByMemberId(String memberId){
        return itemRepository.getItemListCntByMemberId(memberId);
    }

    public List<Item> search(String search,Pagination pagination) {
        if (search == null || search.equals("") || search.equals(" ")){
            return itemRepository.findAll(pagination);
        }else {
            return itemRepository.findAllBySearch(search, pagination);
        }
    }

    public Pagination getPaginationByPage(int page,String search) {
        Pagination pagination = new Pagination();
        int itemListCnt = getItemListCnt(search);
        pagination.pageInfo(page, itemListCnt);
        return pagination;
    }
    public Pagination getPaginationByMemberId(int page,String memberId) {
        Pagination pagination = new Pagination();
        int itemListCnt = getItemListCntByMemberId(memberId);
        pagination.pageInfo(page, itemListCnt);
        return pagination;
    }

    public boolean validateAccessToItem(String itemCode, MemberSession member) {
        Optional<Item> itemOptional = itemRepository.findByItemCode(itemCode);
        Item item = itemOptional.orElseThrow(() -> new IllegalStateException("아이템 검색 실패"));
        // 관리자거나 상품 작성자와 로그인 정보가 일치하면 true , 틀리면 false
        return item.getMemberId().equals(member.getMemberId()) || member.getMemberLevel().equals("관리자");
    }

    public List<Item> getSellerItemByMemberId(String memberId, Pagination pagination,String search){
        return itemRepository.findAllByMemberId(memberId,pagination,search);
    }
    public List<Item> findNewItems(){
        return itemRepository.findNewItems();
    }
    public List<Item> findHitItems(){
        return itemRepository.findHitItems();
    }

    public List<Item> findSellItems(){
        return itemRepository.findBuyItems();
    }

}

