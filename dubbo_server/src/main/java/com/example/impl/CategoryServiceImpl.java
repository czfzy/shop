package com.example.impl;

import com.example.mappers.CategoryMapper;
import com.example.pojo.Category;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        List<Category> list = categoryMapper.list();
        return list;
    }

    @Override
    public List<Category> listLevel2() {
        List<Category> list = categoryMapper.listLevel2();
        return list;
    }
}
