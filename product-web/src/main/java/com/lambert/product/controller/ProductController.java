package com.lambert.product.controller;

import com.lambert.manager.pojo.Product;
import com.lambert.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * http://product.atguigu.com/1231214.html
     */
    @RequestMapping(value="{productId}",method=RequestMethod.GET)
    public ModelAndView queryProductById(@PathVariable("productId")Long productId){
        ModelAndView mv = new ModelAndView("product");

        Product product = this.productService.queryProductById(productId);
        mv.addObject("product", product);
        return mv;
    }

}
