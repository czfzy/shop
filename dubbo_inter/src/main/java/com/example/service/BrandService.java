package com.example.service;

import com.example.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    List<Brand> list();

    PageInfo<Brand> list2(Integer page, Integer size);
}
