package com.lambert.cart.service.impl;

import com.lambert.cart.service.CartService;
import com.lambert.manager.mapper.CartMapper;
import com.lambert.manager.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public boolean saveCart(Cart cart) {
        //初始化
        cart.setId(null);
        cart.setCreated(new Date());
        cart.setUpdated(cart.getCreated());

        //判断购物车中是否有该商品,如果存在数量相加
        Cart param = new Cart();
        param.setUserId(cart.getUserId());
        param.setProductId(cart.getProductId());
        Cart selectCart = this.cartMapper.selectOne(param);
        if (selectCart!=null) {
            //存在该商品
            selectCart.setNum(selectCart.getNum()+cart.getNum());
            selectCart.setUpdated(new Date());
            this.cartMapper.updateByPrimaryKeySelective(selectCart);
            return false;
        }else{
            //不存在该商品
            this.cartMapper.insertSelective(cart);
            return true;
        }
    }

    @Override
    public List<Cart> queryCartByUserId(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartMapper.select(cart);
    }

    @Override
    public void updateCart(Long userId, Long productId, Integer num) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        Cart selectOne = cartMapper.selectOne(cart);
        if (selectOne == null) {
            return;
        }
        selectOne.setNum(num);
        selectOne.setUpdated(new Date());
        cartMapper.updateByPrimaryKeySelective(selectOne);
    }

    @Override
    public void deleteCart(Long userId, Long productId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cartMapper.delete(cart);
    }
}
