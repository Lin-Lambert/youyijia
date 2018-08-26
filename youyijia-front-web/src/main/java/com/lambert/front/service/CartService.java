package com.lambert.front.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambert.front.bean.HttpResult;
import com.lambert.manager.pojo.Cart;
import com.lambert.manager.pojo.Product;
import com.lambert.manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Value("${ATGUIGU_CART_URL}")
    private String ATGUIGU_CART_URL;

    @Value("${ATGUIGU_MANAGER_URL}")
    private String ATGUIGU_MANAGER_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public List<Cart> queryCartList(User user) {
        List<Cart> carts = null;
        try {
            String url = ATGUIGU_CART_URL + "/restful/cart/" + user.getId();
            String jsonData = httpClientUtils.doget(url);
            if (jsonData != null) {
                JavaType valueType = MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class);
                carts = MAPPER.readValue(jsonData, valueType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carts;
    }

    public boolean addProductToCart(User user, Long productId) throws Exception {
        String url = ATGUIGU_CART_URL + "/restful/cart";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("productId", productId);
        String productUrl = ATGUIGU_MANAGER_URL + "/restful/page/api/product/" + productId;
        String jsonProduct = httpClientUtils.doget(productUrl);
        if (jsonProduct != null) {
            Product product = MAPPER.readValue(jsonProduct, Product.class);
            map.put("productTitle", product.getTitle());
            String[] images = product.getImages();
            if (images!=null) {
                map.put("productImage", images[0]);
            }else{
                map.put("productImage", "");
            }

            map.put("productPrice",product.getPrice());
            map.put("num", "1");
        }
        HttpResult httpResult = httpClientUtils.dopost(url, map);
        if (httpResult.getCode() == 201 || httpResult.getCode() == 204) {
            return true;
        }
        return false;
    }

    public boolean updateNum(Long userId, Long productId, Long num) {
        String url = ATGUIGU_CART_URL + "/cart/" + userId + "/" + productId + "/" + num;
        try {
            httpClientUtils.dopost(url);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteCart(Long userId, Long productId) {
        String url = ATGUIGU_CART_URL + "/restful/cart/" + userId + "/" + productId;
        try {
            httpClientUtils.doDelete(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
