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

    /**
     * 상품 조회
     * @param itemCode 상품번호
     * @return 상품 정보
     */
    public Item getItem(String itemCode) {
        // itemCode 로 상품 조회
        itemRepository.itemHitUpWhenInItemDetail(itemCode);
        Optional<Item> item = itemRepository.findByItemCode(itemCode);
        // 상품 null 체크
        // 상품의 옵션 조회
        // 상품에 옵션 넣기
        return item.orElseThrow(() -> new IllegalStateException("Not Found Item"));
    }

    /**
     *
     * @param pagination 페이징 처리 객체
     * @return 페이지에 해당하는 상품 12개
     */
    public List<Item> getItems(Pagination pagination) {
        return itemRepository.findAll(pagination);
    }

    /**
     *
     * @param item 등록할 상품 정보
     * @return 상품 & 상품 옵션 insert -> 결괏값
     */
    public int createItem(Item item) {
        itemRepository.insertItem(item);
        return itemOptionService.insertItemOptions(item.getItemOptions(), item.getItemCode());
    }

    /**
     *
     * @param itemCode 삭제할 상품번호
     * @return 상품 & 상품 옵션 delete -> 결괏값
     */
    public int deleteItem(String itemCode) {
        itemOptionService.deleteByItemCode(itemCode);
        return itemRepository.deleteItem(itemCode);
    }

    /**
     *
     * @param item 수정할 상품번호 & 수정할 내용
     * @return 상품 & 상품 옵션 update -> 결괏값
     */
    public int updateItem(Item item) {
        itemOptionService.modifyItemOption(item.getItemOptions(),item.getItemCode());
        return itemRepository.updateItem(item);
    }

    /**
     *
     * @param search 검색어
     * @return 검색에 따른 상품의 총 갯수 (검색어가 없다면 모든 상품)
     */
    public int getItemListCnt(String search) {
        return itemRepository.getItemListCnt(search);
    }

    /**
     *
     * @param memberId 회원 아이디
     * @return 해당 회원이 등록한 상품의 총 갯수
     */
    public int getItemListCntByMemberId(String memberId){
        return itemRepository.getItemListCntByMemberId(memberId);
    }

    /**
     *
     * @param search 검색어
     * @param pagination 해당 페이지 객체
     * @return 검색어에 해당하는 상품 12개
     */
    public List<Item> search(String search,Pagination pagination) {
        if (search == null || search.equals("") || search.equals(" ")){
            return itemRepository.findAll(pagination);
        }else {
            return itemRepository.findAllBySearch(search, pagination);
        }
    }

    /**
     *
     * @param page 현재 페이지
     * @param search 검색어
     * @return 검색어에 해당하는 현재 페이지 객체
     */
    public Pagination getPaginationByPage(int page,String search) {
        Pagination pagination = new Pagination();
        int itemListCnt = getItemListCnt(search);
        pagination.pageInfo(page, itemListCnt);
        return pagination;
    }

    /**
     *
     * @param page 현재 페이지
     * @param memberId 상점 등록자
     * @return 상점에서 등록한 상품 12개
     */
    public Pagination getPaginationByMemberId(int page,String memberId) {
        Pagination pagination = new Pagination();
        int itemListCnt = getItemListCntByMemberId(memberId);
        pagination.pageInfo(page, itemListCnt);
        return pagination;
    }

    /**
     *
     * @param itemCode 상품번호
     * @param member 로그인 세션 객체
     * @return 상품에 대한 접근 권한
     */
    public boolean validateAccessToItem(String itemCode, MemberSession member) {
        Optional<Item> itemOptional = itemRepository.findByItemCode(itemCode);
        Item item = itemOptional.orElseThrow(() -> new IllegalStateException("아이템 검색 실패"));
        // 관리자거나 상품 작성자와 로그인 정보가 일치하면 true , 틀리면 false
        return item.getMemberId().equals(member.getMemberId()) || member.getMemberLevel().equals("관리자");
    }

    /**
     *
     * @param shopName 상점 이름
     * @param pagination 페이징 객체
     * @param search 검색어
     * @return 상점에서 등록한 검색어에 해당하는 상품 12개
     */
    public List<Item> getSellerItemByMemberId(String shopName, Pagination pagination,String search){
        return itemRepository.findAllByMemberId(shopName,pagination,search);
    }

    /**
     *
     * @return 메인 페이지의 새로운 상품 4개
     */
    public List<Item> findNewItems(){
        return itemRepository.findNewItems();
    }

    /**
     *
     * @return 메인 페이지의 조회수 많은 상품 4개
     */
    public List<Item> findHitItems(){
        return itemRepository.findHitItems();
    }

    /**
     *
     * @return 메인 페이지의 많이 팔린 상품 4개
     */
    public List<Item> findSellItems(){
        return itemRepository.findBuyItems();
    }

}

