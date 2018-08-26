package com.lambert.front.controller;

import com.lambert.front.service.UserService;
import com.lambert.manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "doreg", method = RequestMethod.POST)
    public String doreg(User user) {
        try {
            Boolean bool = userService.doreg(user);
            if (bool) {
                return "redirect:/";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/user/doreg.html";
    }

    @RequestMapping(value = "dologin", method = RequestMethod.POST)
    public String dologin(@RequestParam("loginname") String loginname, @RequestParam("nloginpwd") String nloginpwd, HttpServletRequest request, HttpServletResponse response) {
        try {
            Boolean bool = userService.dologin(loginname, nloginpwd, request, response);
            if (bool) {
                return "redirect:/";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/user/login.html";
    }

}
