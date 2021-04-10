package com.example.mappers;

import com.example.mapper.BaseMapper;
import com.example.pojo.ShopUser;
import org.apache.ibatis.annotations.Param;

public interface ShopUserMapper extends BaseMapper<ShopUser> {
    ShopUser find(@Param("id") String id);

    ShopUser findByName(@Param("name") String name);
}