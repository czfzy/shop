package com.example.service;

import com.example.pojo.ShopUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ShopUserService {
    List<ShopUser> list();

    PageInfo<ShopUser> pageList(Integer page, Integer size);

    ShopUser find(String id);

    ShopUser findByName(String name);

    int add(ShopUser user);

    int save(ShopUser user);

    int delete(Long id);
}
