package com.lambert.front.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambert.common.utils.CookieUtils;
import com.lambert.front.bean.Order;
import com.lambert.front.service.HttpClientUtils;
import com.lambert.front.service.OrderService;
import com.lambert.manager.pojo.Product;
import com.lambert.manager.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Value("${ATGUIGU_MANAGER_URL}")
    private String ATGUIGU_MANAGER_URL;

    @Value("${ATGUIGU_SSO_URL}")
    private String ATGUIGU_SSO_URL;

    @Value("${ATGUIGU_ORDER_URL}")
    private String ATGUIGU_ORDER_URL;

    @Autowired
    private OrderService orderService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RequestMapping("orderinfo")
    public ModelAndView toOrderInfo(){
        ModelAndView  mv = new ModelAndView("orderinfo");
        return mv;
    }

    /**
     * 显示下单页
     */
    @RequestMapping(value="/{productId}")
    public ModelAndView toOrder(@PathVariable("productId") Long productId){
        ModelAndView mv = new ModelAndView("order");

        try {
            String url = ATGUIGU_MANAGER_URL+"/restful/page/api/product/"+productId;

            String jsonData = this.httpClientUtils.doget(url);
            if (jsonData!=null) {
                Product product = MAPPER.readValue(jsonData, Product.class);
                mv.addObject("product", product);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return mv;
    }

    /**
     * 下单
     */
    @RequestMapping(value="/submit",method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doOrder(Order order, HttpServletRequest request){
        Map<String, Object> result = new HashMap<String, Object>();

        //设置用户
        String ticket = CookieUtils.getCookieValue(request, "ATGUIGU_TICKET", true);

        //根据ticket查询用户信息
        if (!"".equals(ticket)) {
            String url = ATGUIGU_SSO_URL+"/user/"+ticket;
            try {
                String jsonData = this.httpClientUtils.doget(url);
                if (jsonData!=null) {
                    User user = MAPPER.readValue(jsonData, User.class);
                    if (user!=null) {
                        order.setUserId(user.getId());
                        order.setBuyerNick(user.getUsername());
                        String orderId = this.orderService.createOrder(order);

                        if (StringUtils.isNoneBlank(orderId)) {
                            //创建订单成功
                            result.put("status", "200");
                            result.put("data", orderId);
                        }else{
                            result.put("status", "500");
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 显示下单成功页
     */
    @RequestMapping(value="success",method=RequestMethod.GET)
    public ModelAndView success(@RequestParam("id") String orderId){
        ModelAndView mv = new ModelAndView("success");
        try {
            String url = ATGUIGU_ORDER_URL+"/order/query/"+orderId;
            String jsonData = this.httpClientUtils.doget(url);
            if (jsonData!=null) {
                Order order = MAPPER.readValue(jsonData, Order.class);
                mv.addObject("order", order);
                mv.addObject("date", new DateTime().plusDays(2).toString("MM月dd日"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mv;
    }

}
