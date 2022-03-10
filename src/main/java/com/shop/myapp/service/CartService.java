package com.shop.myapp.service;

import com.shop.myapp.dto.Cart;
import com.shop.myapp.repository.CartRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class CartService {

    private final CartRepository cartRepository;

    public CartService(@Autowired SqlSession sqlSession) {
        this.cartRepository = sqlSession.getMapper(CartRepository.class);
    }

    public int insertCart(Cart cart) {
        return cartRepository.insertCart(cart);
    }

    public List<Cart> findCartDetailByMemberId(String memberId){
        return cartRepository.findByMemberId(memberId);
    }

    public int deleteByMemberId(String memberId){
        return cartRepository.deleteCartByMemberId(memberId);

    }

    public int deleteByCartId(String cartId){
        return cartRepository.deleteCartByCartId(cartId);
    }

    public List<Cart> findSelectCartByCartIds(List<String> cartCods){
        return cartRepository.findSelectCartByCartCodes(cartCods);
    }
    public Optional<Cart> findByCartId(String cartId){
        return cartRepository.findByCartId(cartId);

    }
    public int amountSetByCartId(String cartId,String mathSign){
        return cartRepository.amountSetByCartId(cartId,mathSign);
    }

    public Optional<Cart> findMyCartByOptionCode(String memberId, String optionCode){
        return cartRepository.findMyCartByOptionCode(memberId,optionCode);
    }

    public void validateMemberId(String memberId,String cartCode){
        Optional<Cart> byCartId = cartRepository.findByCartId(cartCode);
        Cart cart = byCartId.orElseThrow(() -> new IllegalStateException("장바구니 정보 없음"));
        if (!cart.getMemberId().equals(memberId)){
            throw new IllegalStateException("권한 없음");
        }
    }

}
