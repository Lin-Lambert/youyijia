package com.lambert.cart.controller;

import com.lambert.cart.service.CartService;
import com.lambert.manager.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "cart", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveCart(Cart cart) {

        try {
            boolean bool = cartService.saveCart(cart);
            if (bool) {
                return ResponseEntity.status(HttpStatus.CREATED).body(true);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @RequestMapping(value = "cart/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Cart>> queryCartByUserId(@PathVariable("userId") Long userId) {
        try {
            List<Cart> carts = cartService.queryCartByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(carts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "cart/{userId}/{productId}/{num}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> updateCart(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId, @PathVariable("num") Integer num) {
        try {
            cartService.updateCart(userId, productId, num);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(value = "cart/{userId}/{productId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteCart(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) {
        try {
            cartService.deleteCart(userId, productId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
