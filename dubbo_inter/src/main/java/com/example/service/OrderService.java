package com.example.service;

import com.example.pojo.OrderDelivery;
import com.example.pojo.OrderItem;
import com.example.pojo.OrderSub;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<OrderSub> list();

    PageInfo<OrderSub> pageList(Long shopId, Integer orderStatus, Integer page, Integer size);

    List<OrderItem> find(Long id);

    OrderDelivery getDelivery(Long id);

    Map count(Long shopId);
}
