package com.example.impl;

import cn.hutool.core.date.DateUtil;
import com.example.mappers.*;
import com.example.pojo.*;
import com.example.service.OrderService;
import com.example.tool.ConstantKey;
import com.example.tool.DateTimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSubMapper orderSubMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderDeliveryMapper orderDeliveryMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<OrderSub> list() {
        return orderSubMapper.selectAll();
    }

    @Override
    public PageInfo<OrderSub> pageList(Long shopId, Integer orderStatus, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Example example = new Example(OrderSub.class);
        if (shopId != null && shopId > 0) {
            example.createCriteria().andEqualTo("shopId", shopId);
        }
        if (orderStatus != null && orderStatus >= 0) {
            example.createCriteria().andEqualTo("orderStatus", orderStatus);
        }
        List<OrderSub> list = orderSubMapper.selectByExample(example);
        PageInfo<OrderSub> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public List<OrderItem> find(Long subId) {
        Example example = new Example(OrderItem.class);
        example.createCriteria().andEqualTo("subId", subId);
        List<OrderItem> list = orderItemMapper.selectByExample(example);
        return list;
    }

    @Override
    public OrderDelivery getDelivery(Long subId) {
        Example example = new Example(OrderDelivery.class);
        example.createCriteria().andEqualTo("subId", subId);
        OrderDelivery tbOrderDeliverys = orderDeliveryMapper.selectOneByExample(example);
        return tbOrderDeliverys;
    }

    @Override
    public Map count(Long shopId) {
        int today = DateTimeUtil.toTimestamp(DateUtil.beginOfDay(new Date()));
        int tomorrow = DateTimeUtil.toTimestamp(DateUtil.endOfDay(new Date()));
        int yearday = DateTimeUtil.toTimestamp(DateUtil.beginOfDay(DateUtil.yesterday()));
        int lastWeek = DateTimeUtil.toTimestamp(DateUtil.beginOfDay(DateUtil.lastWeek()));
        int nowMonth = DateTimeUtil.toTimestamp(DateUtil.beginOfMonth(new Date()));
        int endMonth = DateTimeUtil.toTimestamp(DateUtil.endOfMonth(new Date()));
        int todayCount = orderSubMapper.countByTime(shopId, today, tomorrow);
        int proCount = orderSubMapper.countByTime(shopId, yearday, today);
        int lastWeekCount = orderSubMapper.countByTime(shopId, lastWeek, tomorrow);
        int monthCount = orderSubMapper.countByTime(shopId, nowMonth, tomorrow);

        Example example = new Example(Member.class);
        int userCount = memberMapper.selectCountByExample(example);

        Example example1 = new Example(OrderSub.class);
        example1.selectProperties("orderId").setDistinct(true);
        if (shopId > 0) {
            example1.createCriteria().andEqualTo("shopId", shopId);
        }
        int orderCount = orderSubMapper.selectCountByExample(example1);
        Double priceCount = orderSubMapper.sumTotalPrice(shopId);

        Example example2 = new Example(Goods.class);
        int goodsCount = goodsMapper.selectCountByExample(example2);

        List<String> colums = new ArrayList<>();
        List<OrderCount> counts = orderSubMapper.orderStatusList(shopId);
        for (OrderCount count : counts) {
            System.out.println();
            if (ConstantKey.UNPAID.equals(count.getName())) {
                count.setName("未支付");
            } else if (ConstantKey.INVALID_ORDER.equals(count.getName())) {
                count.setName("订单失效");
            } else if (ConstantKey.SEAL_THE_DEAL.equals(count.getName())) {
                count.setName("完成交易");
            } else if (ConstantKey.MERCHANDISE_RETURN.equals(count.getName())) {
                count.setName("商品退货");
            }
            colums.add(count.getName());
        }

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("todayCount", todayCount); //今日订单数
        map.put("proCount", proCount); //昨日订单数
        map.put("lastWeekCount", lastWeekCount); //上周订单数
        map.put("monthCount", monthCount); //本月订单数
        map.put("userCount", userCount);
        map.put("orderCount", orderCount);
        map.put("priceCount", priceCount);
        map.put("goodsCount", goodsCount);
        map.put("column", colums);
        map.put("orderCountDatas", counts);
        return map;
    }
}
