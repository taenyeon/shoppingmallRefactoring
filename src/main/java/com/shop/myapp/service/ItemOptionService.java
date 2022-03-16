package com.shop.myapp.service;

import com.shop.myapp.dto.ItemOption;
import com.shop.myapp.dto.OrderDetail;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class ItemOptionService {
    private final com.shop.myapp.repository.ItemOptionRepository itemOptionRepository;

    public ItemOptionService(@Autowired SqlSession sqlSession) {
        this.itemOptionRepository = sqlSession.getMapper(com.shop.myapp.repository.ItemOptionRepository.class);
    }

    /**
     *
     * @param optionCode 상품 옵션 번호
     * @return 상품 옵션 상세보기
     */
    public Optional<ItemOption> findByOptionCode(String optionCode) {
        return itemOptionRepository.findByOptionCode(optionCode);
    }

    /**
     * 주문할때 해당 옵션의 가격, 재고 확인
     * @param optionCode 상품 옵션 번호
     * @return 주문에 참고할 상품 옵션 정보
     */
    public Optional<ItemOption> findByOptionCodeForOrder(String optionCode) {
        return itemOptionRepository.findByOptionCodeForOrder(optionCode);
    }

    /**
     * 상품 등록시, 옵션 등록 처리
     * @param options 상품 옵션 정보 리스트
     * @param itemCode 상품 번호
     * @return 상품 옵션 DB 저장 결과
     */
    public int insertItemOptions(List<ItemOption> options, String itemCode) {
        options.removeAll(Collections.singletonList(null));
        for (ItemOption itemOption : options) {
            itemOption.setItemCode(itemCode);
        }
        return itemOptionRepository.insertItemOptions(options);
    }

    /**
     * 상품 수정시, 옵션 수정 처리
     * @param options 상품 옵션 정보 리스트
     * @param itemCode 상품 번호
     * @return 옵션 DB 추가 & 업데이트 결과
     */
    public int modifyItemOption(List<ItemOption> options, String itemCode) {
        // 기존 옵션 삭제
        int result = deleteByItemCode(itemCode);
        // 새로 옵션 추가 & 기존 옵션 수정 merge문
        return itemOptionRepository.insertItemOptions(options);
    }

    /**
     * 주문 완료시, 옵션의 재고 수정
     * @param orderDetails 주문 상세
     * @return 옵션 재고 DB 업데이트 결과
     */
    public int modifyItemOptionAfterPay(List<OrderDetail> orderDetails) {
        // 계산 후, 주문량만큼 아이템의 숫자 갱신
        int result = 0;
        for (OrderDetail orderDetail : orderDetails) {
            result += itemOptionRepository.modifyItemOptionStockByOptionCode(orderDetail);
        }
        return result;
    }

    /**
     * 환불시, 옵션의 재고 수정
     * @param orderDetail 주문 상세
     * @return 옵션 재고 DB 업데이트 결과
     */
    public int modifyItemOptionAfterRefund(OrderDetail orderDetail){
        return itemOptionRepository.modifyItemOptionStockByOptionCodeWhenRefund(orderDetail);
    }

    /**
     * @deprecated 상품 옵션 삭제시, isDeleted 컬럼을 통해 삭제 처리하도록 수정하여, 현재 사용 x
     * @param optionCode 옵션 번호
     * @return 옵션 DB 삭제 결과
     */
    public int deleteByOptionCode(String optionCode) {
        return itemOptionRepository.isDeleteItemOption(optionCode);
    }


    /**
     * 상품 삭제 처리시, 상품에 해당하는 옵션들 삭제 처리 (isDeleted)
     * @param itemCode 상품번호
     * @return 옵션 DB 삭제 처리(isDeleted) 결과
     */
    public int deleteByItemCode(String itemCode) {
        return itemOptionRepository.isDeleteByItemCode(itemCode);
    }


}

