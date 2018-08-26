package com.lambert.manager.service;

import com.lambert.manager.pojo.Product;

public interface ProductService extends BaseService<Product>{
    void saveProductAndDeac(Product product, String editorValue);
}
