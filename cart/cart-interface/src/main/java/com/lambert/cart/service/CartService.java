package com.lambert.cart.service;

import com.lambert.manager.pojo.Cart;

import java.util.List;

public interface CartService {
    public boolean saveCart(Cart cart) ;

    List<Cart> queryCartByUserId(Long userId);

    void updateCart(Long userId, Long productId, Integer num);

    void deleteCart(Long userId, Long productId);
}
