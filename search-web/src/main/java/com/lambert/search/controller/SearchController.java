package com.lambert.search.controller;

import com.lambert.search.pojo.SolrResult;
import com.lambert.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "search.html")
    public ModelAndView tosearch(String queryString, String cid, String price, String page,String sort) {
        ModelAndView mv = new ModelAndView("search");
        SolrResult sorResult;
        try {
            sorResult = this.searchService.search(queryString,cid,price,page,sort);
            mv.addObject("result", sorResult);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mv.addObject("queryString", queryString);
        mv.addObject("cid", cid);
        mv.addObject("price", price);
        mv.addObject("page", page);
        mv.addObject("sort", sort);
        return mv;
    }

}
