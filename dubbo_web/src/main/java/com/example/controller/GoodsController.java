package com.example.controller;

import com.example.pojo.*;
import com.example.service.GoodsService;
import com.example.tool.HttpResult;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    //@Autowired
    @Reference
    private GoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    //商品更新缓存
    @GetMapping("/test")
    public void test() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date());
        String hashKey = "HashKey";
        String name = String.valueOf("123456");
        String value = String.valueOf("商品更新缓存");
        if (redisTemplate.opsForHash().hasKey(hashKey, name)) {
            redisTemplate.opsForHash().get(hashKey, "SmallKey");
            Object redisValue = redisTemplate.opsForHash().get(hashKey, name);
            System.out.println(time + " 已缓存数据:" + redisValue);
        } else {
            redisTemplate.opsForHash().put(hashKey, name, value);
            redisTemplate.expire(hashKey, 1, TimeUnit.MINUTES);
            System.out.println(time + " 新添加数据:" + value);
        }
    }

    //商品列表
    @PostMapping(value = "/list")
    public HttpResult list(@RequestBody GoodsQuery queryCondition) {
        PageInfo<Goods> list = goodsService.list(queryCondition);
        return HttpResult.success(list);
    }

    //添加产品及商品
    @PostMapping("/add")
    public HttpResult add(@RequestBody ProductVo tbProductVo) {
        Product product = (Product) tbProductVo;
        int res = goodsService.add(product, tbProductVo.getExtrAttribute(), tbProductVo.getIntroduce());
        return HttpResult.success(res);
    }

    //保存商品 mq
    @PostMapping(value = "/saveGoods")
    public HttpResult saveGoods(@RequestBody Goods info) {
        int result = goodsService.saveGoods(info);
        return HttpResult.success(result);
    }

    //更新图片附件
    @ResponseBody
    @PostMapping("/relatedImage")
    public HttpResult relatedImage(@RequestBody Map<String, Object> map) {
        Long goodsId = Long.valueOf((Integer) map.get("goodsId"));
        List<String> imgs = (List<String>) map.get("img");
        goodsService.deleteImg(goodsId); //删除图片
        //添加图片
        GoodsAttach goodsAttach = new GoodsAttach();
        goodsAttach.setGoodsId(goodsId);
        int result = 0;
        for (String img : imgs) {
            goodsAttach.setPath(img);
            goodsService.saveGoodsAttach(goodsAttach);
            goodsAttach.setId(0l);
            result++;
        }
        return HttpResult.success(result);
    }

    //查看图片
    @ResponseBody
    @GetMapping("/findImage")
    public HttpResult findImage(Long goodsId) {
        List<GoodsAttach> goodsAttachs = goodsService.findImage(goodsId);
        List<String> list = new ArrayList<>();
        for (GoodsAttach goodsAttach : goodsAttachs) {
            list.add(goodsAttach.getPath());
        }
        return HttpResult.success(list);
    }

    //上传图片
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public HttpResult uploadImage(@RequestParam("file") MultipartFile file) {
        String result = null;
        return HttpResult.success(result);
    }

    //删除图片
    @RequestMapping(value = "/deleteImage", method = RequestMethod.GET)
    public HttpResult deleteImage(@RequestParam("fileUrl") String fileUrl) {
        String result = null;
        return HttpResult.success(result);
    }
}
