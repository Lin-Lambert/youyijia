package com.lambert.front.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.mapper.Mapper;
import com.lambert.common.utils.CookieUtils;
import com.lambert.front.bean.UserThreadLocal;
import com.lambert.front.service.HttpClientUtils;
import com.lambert.manager.pojo.User;
import com.lambert.manager.service.com.lambert.manager.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor{

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Value("${ATGUIGU_SSO_URL}")
    private String ATGUIGU_SSO_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = CookieUtils.getCookieValue(httpServletRequest, "ATGUIGU_TICKET", true);
        if (ticket == null) {
            UserThreadLocal.set(null);
            return true;
        }
        String url = ATGUIGU_SSO_URL + "/user/"+ticket;
        String jsonData = httpClientUtils.doget(url);
        if (jsonData == null) {
            UserThreadLocal.set(null);
            return true;
        }
        User user = MAPPER.readValue(jsonData, User.class);
        UserThreadLocal.set(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
