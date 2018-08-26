package com.lambert.test;

import com.lambert.front.bean.HttpResult;
import com.lambert.front.service.HttpClientUtils;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@ContextConfiguration(locations = {"classpath:spring/applicationContext-httpclient.xml"})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class HttpClientTest {

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Test
    public void test1() {
        String url = "http://manage.youyijia.com/restful/page/api/product/173155181";
        HttpResult dopost = null;
        try {
            dopost= httpClientUtils.dopost(url);
            System.out.println(dopost.getBody());
            System.out.println(dopost.getBody() == null);
            System.out.println(dopost.getBody() == "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        System.out.println(new DateTime().plusDays(2).toString("y年m月d日"));
    }
}
