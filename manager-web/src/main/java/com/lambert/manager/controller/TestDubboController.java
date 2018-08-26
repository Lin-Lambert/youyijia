package com.lambert.manager.controller;

import com.lambert.manager.service.TestDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestDubboController {

    @Autowired
    private TestDubboService testDubboService;

    @RequestMapping(value="test")
    @ResponseBody
    public ResponseEntity<String> getDate(){

        String strdate = this.testDubboService.getDate();
        return ResponseEntity.status(HttpStatus.OK).body(strdate);
    }

}
