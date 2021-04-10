package com.example.controller;

import com.example.pojo.Brand;
import com.example.service.BrandService;
import com.example.tool.HttpResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.apache.dubbo.config.annotation.Reference;

@RestController
@RequestMapping("/brand")
public class BrandController {
    //@Autowired
    @Reference
    private BrandService brandService;

    //品牌列表
    @GetMapping("/list")
    public HttpResult list() {
        System.out.println("list");
        List<Brand> list = brandService.list();
        return HttpResult.success(list);
    }

    //品牌列表-分页
    @GetMapping("/pageList")
    public HttpResult pageList(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        PageInfo<Brand> list = brandService.list2(page, size);
        return HttpResult.success(list);
    }
}
