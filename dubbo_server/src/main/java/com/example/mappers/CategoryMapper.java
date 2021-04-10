package com.example.mappers;

import com.example.mapper.BaseMapper;
import com.example.pojo.Category;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> list();

    List<Category> listLevel2();
}