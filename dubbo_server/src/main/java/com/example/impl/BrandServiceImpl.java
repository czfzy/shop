package com.example.impl;

import com.example.mappers.BrandMapper;
import com.example.pojo.Brand;
import com.example.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> list() {
        List<Brand> list = brandMapper.selectAll();
        return list;
    }

    @Override
    public PageInfo<Brand> list2(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Example example = new Example(Brand.class);
        example.setOrderByClause("id desc");
        List<Brand> list = brandMapper.selectByExample(example);
        PageInfo<Brand> info = new PageInfo<>(list);
        return info;
    }
}
