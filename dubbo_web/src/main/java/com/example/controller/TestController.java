package com.example.controller;

import com.example.tool.DefinitionException;
import com.example.tool.HttpResult;
import com.example.tool.ResultEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/find")
    public HttpResult getUserInfo(){
        return new HttpResult<>(ResultEnum.SUCCESS, "content");
    }

    //null异常
    @GetMapping("/nullException")
    public HttpResult getNullException() {
        HttpResult result = null;
        result.getCode();
        return result; //"message": "服务器出错"
    }

    //自定义异常
    @GetMapping("/deException")
    public HttpResult<Object> DeExcpetion() {
        return HttpResult.defineError(new DefinitionException("出错了"));
    }

    //自定义异常2
    @GetMapping("/deException2")
    public HttpResult<Object> DeExcpetion2() {
        return HttpResult.defineError(new DefinitionException(ResultEnum.NOT_PARAM));
    }
}
