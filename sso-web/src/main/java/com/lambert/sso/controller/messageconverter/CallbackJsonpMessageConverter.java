package com.lambert.sso.controller.messageconverter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonEncoding;

public class CallbackJsonpMessageConverter extends MappingJackson2HttpMessageConverter {

    // 支持jsonp,接收方法名
    private String callbackName;
    

    public String getCallbackName() {
        return callbackName;
    }


    public void setCallbackName(String callbackName) {
        this.callbackName = callbackName;
    }


    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        //回调方法名称
        String methodName = request.getParameter(callbackName);

        if (StringUtils.isEmpty(methodName)) {
            //不需要做jsonp的支持,返回原来的json格式数据即可
            super.writeInternal(object, outputMessage);
        }else{
            //要做jsonp的支持
            JsonEncoding jsonEncoding = getJsonEncoding(outputMessage.getHeaders().getContentType());

            //定义返回值
            String result = methodName+"("+super.getObjectMapper().writeValueAsString(object)+")";

            IOUtils.write(result, outputMessage.getBody(), jsonEncoding.getJavaName());

        }
    }

}
