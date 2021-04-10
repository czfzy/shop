package com.example.impl;

import com.example.mappers.GoodsAttachMapper;
import com.example.mappers.GoodsMapper;
import com.example.mappers.ProductExtMapper;
import com.example.mappers.ProductMapper;
import com.example.pojo.*;
import com.example.service.GoodsService;
import com.example.tool.ConstantKey;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductExtMapper productExtMapper;
    @Autowired
    private GoodsAttachMapper goodsAttachMapper;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public PageInfo<Goods> list(GoodsQuery condition) {
        PageHelper.startPage(condition.getPage(), condition.getSize());
        Example example = new Example(Goods.class);
        //组合查询条件，条件会互相影响
        /*if (condition.getId() != null && condition.getId() > 0) {
            example.createCriteria().andEqualTo("id", condition.getId());
        }
        if (StringUtils.isNotEmpty(condition.getGoodsName())) {
            example.createCriteria().andEqualTo("goodsName", condition.getGoodsName());
        }
        if (condition.getShopId() != null && condition.getShopId() > 0) {
            example.createCriteria().andEqualTo("shopId", condition.getShopId());
        }
        if (condition.getStatus() != null && condition.getStatus() > 0) {
            example.createCriteria().andEqualTo("status", condition.getStatus());
        }*/

        List<Goods> goodsInfos = goodsMapper.selectByExample(example);
        PageInfo<Goods> info = new PageInfo<>(goodsInfos);
        return info;
    }

    @Override
    @Transactional
    public int add(Product product, String introduce, String extrattribute) {
        productMapper.insertUseGeneratedKeys(product); //添加产品
        Long productId = product.getId(); //最新产品ID

        ProductExt productExt = new ProductExt();
        productExt.setProductId(productId);
        productExt.setIntroduce(introduce);
        productExt.setExtrattribute(extrattribute);
        productExtMapper.insertSelective(productExt);

        int result = 0;
        List<Goods> list = product.getGoodsList();
        for (Goods goods : list) {
            goods.setProductId(productId);
            goods.setShopId(product.getShopId());
            //goods.setStatus(1);
            goodsMapper.insertSelective(goods);
            result++;
        }

        return result;
    }

    @Override
    public int saveGoods(Goods goods) {
        int count = goodsMapper.updateByPrimaryKeySelective(goods);
        //更新商品信息，生产者发送商品消息
        rocketMQTemplate.convertAndSend(ConstantKey.TOPIC_NAME, goods);
        return count;
    }

    @Override
    public List<GoodsAttach> findImage(Long goodsId) {
        Example example = new Example(GoodsAttach.class);
        example.createCriteria().andEqualTo("goodsId", goodsId);
        return goodsAttachMapper.selectByExample(example);
    }

    @Override
    public int deleteImg(Long goodsId) {
        Example example = new Example(GoodsAttach.class);
        example.createCriteria().andEqualTo("goodsId", goodsId);
        return goodsAttachMapper.deleteByExample(example);
    }

    @Override
    public void saveGoodsAttach(GoodsAttach goodsAttach) {
        goodsAttachMapper.insertSelective(goodsAttach);
    }
}
