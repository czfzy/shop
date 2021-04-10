package com.example.service;

import com.example.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> list();

    List<Category> listLevel2();
}
