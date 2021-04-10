package com.example.service;

import com.example.pojo.Goods;
import com.example.pojo.GoodsAttach;
import com.example.pojo.GoodsQuery;
import com.example.pojo.Product;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface GoodsService {
    PageInfo<Goods> list(GoodsQuery condition);

    int add(Product product, String introduce, String extrattribute);

    int saveGoods(Goods goodsInfo);

    void saveGoodsAttach(GoodsAttach goodsAttach);

    List<GoodsAttach> findImage(Long goodsId);

    int deleteImg(Long goodsId);
}
