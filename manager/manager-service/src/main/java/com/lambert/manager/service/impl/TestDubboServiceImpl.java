package com.lambert.manager.service.impl;

import com.lambert.manager.service.TestDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TestDubboServiceImpl implements TestDubboService {

    @Override
    public String getDate() {
        return "two";
    }
}
