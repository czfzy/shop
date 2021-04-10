package com.example.mapper;

import com.example.pojo.BaseEntity;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface BaseMapper<T extends BaseEntity> extends Mapper<T>, MySqlMapper<T> {

}
