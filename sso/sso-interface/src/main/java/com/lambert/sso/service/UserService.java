package com.lambert.sso.service;

import com.lambert.manager.pojo.User;

public interface UserService {

    public Boolean check(String param, Integer type) throws Exception;

    public void register(User user);

    public String login(String username, String password) throws Exception ;

    public User queryByTicket(String ticket) throws Exception;

}
