package com.shop.myapp.repository;

import com.shop.myapp.dto.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartRepository {
    int insertCart(Cart cartItem);
    List<Cart> findByMemberId(@Param("memberId") String memberId);
    int deleteCartByCartId(@Param("cartId") String cartId);
    int deleteCartByMemberId(@Param("memberId") String memberId);
    List<Cart> findAll();
    Optional<Cart> findByCartId(String cartId);
    List<Cart> findSelectCartByCartCodes(List<String> cartCodes);
    int amountSetByCartId(@Param("cartId") String cartId, @Param("mathSign") String mathSign);
    Optional<Cart> findMyCartByOptionCode(@Param("memberId") String memberId, @Param("optionCode") String optionCode);
}
