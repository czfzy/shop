package com.example.mappers;

import com.example.mapper.BaseMapper;
import com.example.pojo.OrderCount;
import com.example.pojo.OrderSub;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderSubMapper extends BaseMapper<OrderSub> {
    Integer countByTime(@Param("shopId") Long shopId, @Param("start") int start, @Param("end") int end);

    Double sumTotalPrice(@Param("shopId") Long shopId);

    List<OrderCount> orderStatusList(@Param("shopId") Long shopId);
}