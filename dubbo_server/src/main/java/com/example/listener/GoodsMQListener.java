package com.example.listener;

import com.example.mappers.GoodsMapper;
import com.example.pojo.Goods;
import com.example.service.GoodsService;
import com.example.tool.ConstantKey;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.concurrent.TimeUnit;

//消费者消息商品信息
@RocketMQMessageListener(
        topic = ConstantKey.TOPIC_NAME, //生产者和消费者topic相同
        consumerGroup = "consumer-group", //生产者和消费者group可以不相同
        selectorExpression = "*"
)
@Component //注入spring容器
public class GoodsMQListener implements RocketMQListener<Goods> {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(Goods goods) {
        int result = 0;
        int count = 0;
        Long goodsId = goods.getId();
        System.out.println("goodsId:" + goodsId);
        Example example = new Example(Goods.class);
        if (goodsId != null && goodsId > 0) {
            example.createCriteria().andEqualTo("id", goodsId);
            count = goodsMapper.selectCountByExample(example);
        }
        if (count == 0) {
            result = goodsMapper.insertSelective(goods); //增加商品
            System.out.println("count:" + count + " 增加商品:" + result);
        } else {
            result = goodsMapper.updateByPrimaryKeySelective(goods); //修改商品
            System.out.println("count:" + count + " 修改商品:" + result);
        }

        //更新缓存
        String hashKey = "HashKey";
        String name = String.valueOf(goods.getId());
        String value = String.valueOf(goods);
        redisTemplate.opsForHash().put(hashKey, name, value);
        redisTemplate.expire(hashKey, 1, TimeUnit.MINUTES);
    }
}
