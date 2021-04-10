package com.example.controller;

import com.csvreader.CsvWriter;
import com.example.pojo.OrderDelivery;
import com.example.pojo.OrderItem;
import com.example.pojo.OrderSub;
import com.example.service.OrderService;
import com.example.tool.DefinitionException;
import com.example.tool.HttpResult;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    @Reference
    private OrderService orderService;

    //订单列表
    @GetMapping(value = "/pageList")
    public HttpResult pageList(Long shopId, Integer orderStatus, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        PageInfo<OrderSub> list = orderService.pageList(shopId, orderStatus, page, size);
        return HttpResult.success(list);
    }

    //订单详情
    @GetMapping(value = "/find/{id}")
    public HttpResult find(@PathVariable("id") Long subId) {
        List<OrderItem> list = orderService.find(subId);
        if (list != null && list.size() <= 0) {
            return HttpResult.defineError(new DefinitionException("获取商品列表为空"));
        }
        return HttpResult.success(list);
    }

    //订单配送详情
    @GetMapping(value = "/getDeliver/{id}")
    public HttpResult getDeliver(@PathVariable("id") Long subId) {
        OrderDelivery list = orderService.getDelivery(subId);
        if (list == null) {
            return HttpResult.defineError(new DefinitionException("暂无次订单配送信息"));
        }
        return HttpResult.success(list);
    }

    //订单统计
    @GetMapping(value = "/count")
    public ResponseEntity count(@RequestParam(value = "shopId", defaultValue = "0") Long shopId) {
        Map list = orderService.count(shopId);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    //导出订单csv
    @GetMapping("/download")
    public void download(HttpServletResponse response, Long shopId, Integer orderStatus, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        CsvWriter csv = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            //创建CsvWriter对象
            csv = new CsvWriter(os, ',', Charset.forName("utf-8"));
            String[] headers = {"ID", "订单Id", "会员ID", "店铺名称", "付款时间", "地址", "创建时间", "更新时间"};
            csv.writeRecord(headers);
            //获取子订单列表
            List<OrderSub> orderSubs = orderService.list();
            int result = 0;
            for (OrderSub orderSub : orderSubs) {
                List<String> list = new ArrayList<>();
                list.add("'" + String.valueOf(orderSub.getId()));
                list.add("'" + String.valueOf(orderSub.getOrderId()));
                list.add(String.valueOf(orderSub.getUid()));
                list.add(String.valueOf(orderSub.getShopName()));
                list.add("'" + sdf.format(orderSub.getPayTime()));
                list.add(orderSub.getAddress());
                list.add("'" + sdf.format(orderSub.getCreateTime()));
                list.add("'" + sdf.format(orderSub.getUpdateTime()));
                String[] array = list.toArray(new String[list.size()]);
                csv.writeRecord(array);
                result++;
            }

            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("订单.csv", "UTF-8")); //显示中文
            System.out.println("Download file ok");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csv != null) {
                csv.close();
            }
        }
    }
}
