package com.lambert.front.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambert.common.utils.CookieUtils;
import com.lambert.front.bean.HttpResult;
import com.lambert.manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private HttpClientUtils httpClientUtils;

    private static final String ATGUIGU_TICKET = "ATGUIGU_TICKET";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${ATGUIGU_SSO_URL}")
    private String ATGUIGU_SSO_URL;

    public Boolean doreg(User user) throws Exception {

        String url = ATGUIGU_SSO_URL + "/user/register";

        Map<String, Object> map = new HashMap<>();

        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("phone", user.getPhone());
        map.put("email", user.getEmail());

        HttpResult httpResult = this.httpClientUtils.dopost(url, map);
        if (httpResult.getCode() == 200) {
            //注册成功
            return true;
        } else {
            //注册失败
            return false;
        }

    }

    public Boolean dologin(String loginname, String nloginpwd, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = ATGUIGU_SSO_URL + "/user/login";
        Map<String, Object> map = new HashMap<>();
        map.put("username", loginname);
        map.put("password", nloginpwd);
        HttpResult httpResult = httpClientUtils.dopost(url, map);
        if (httpResult.getCode() == 200) {
            System.out.println(httpResult.getBody());
            if (httpResult.getBody() != null && httpResult.getBody().length() != 0) {
                CookieUtils.setCookie(request, response, ATGUIGU_TICKET, httpResult.getBody(), true);
                return true;
            }
        }
        return false;
    }

    public User queryByTicket(String ticket) {
        try {
            String url = ATGUIGU_SSO_URL + "/user/" + ticket;
            String httpResult = httpClientUtils.doget(url);
            if (httpResult != null && httpResult.length() != 0) {
                User user = MAPPER.readValue(httpResult, User.class);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
