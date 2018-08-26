package com.lambert.sso.controller;

import com.lambert.common.utils.CookieUtils;
import com.lambert.manager.pojo.User;
import com.lambert.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "check/{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param") String param, @PathVariable("type") Integer type) {
        Boolean bool = false;
        try {
            bool = userService.check(param, type);
            return ResponseEntity.status(HttpStatus.OK).body(bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bool);
    }

    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<String> register(User user) {
        try {
            this.userService.register(user);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            String ticket = userService.login(username, password);
            if (ticket != null) {
                return ResponseEntity.status(HttpStatus.OK).body(ticket);
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    /**
     * 根据ticket查询用户信息
     */
    @RequestMapping(value="{ticket}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> queryByTicket(@PathVariable("ticket") String ticket){

        try {
            User user = this.userService.queryByTicket(ticket);

            if (user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
