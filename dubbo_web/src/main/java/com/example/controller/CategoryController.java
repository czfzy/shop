package com.example.controller;

import com.csvreader.CsvWriter;
import com.example.pojo.Category;
import com.example.service.CategoryService;
import com.example.tool.FileUtil;
import com.example.tool.HttpResult;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "商品分类管理")
@RestController
@RequestMapping("/category")
public class CategoryController {
    //@Autowired
    @Reference
    private CategoryService categoryService;

    @ApiOperation(value = "商品分类列表")
    @GetMapping(value = "/pageList")
    public HttpResult pageList() {
        List<Category> list = categoryService.list();
        PageInfo<Category> info = new PageInfo<>(list);
        return HttpResult.success(info);
    }

    @ApiOperation(value = "两级商品分类")
    @GetMapping(value = "/listLevel2")
    public HttpResult listLevel2() {
        List<Category> categories = categoryService.listLevel2();
        return HttpResult.success(categories);
    }

    //生成商品分类csv
    @GetMapping(value = "/createCsv")
    public HttpResult createCsv() {
        String filePath = "/Users/liutao/Downloads/商品分类.csv";
        int result = 0;
        CsvWriter csv = null;
        try {
            csv = new CsvWriter(filePath, ',', Charset.forName("utf-8"));
            String[] headers = {"id", "分类名称", "父ID", "层级", "顺序", "状态"};
            csv.writeRecord(headers);
            List<Category> categoryInfos = categoryService.list();
            for (Category categoryInfo : categoryInfos) {
                List<String> contentlist = new ArrayList<>();
                contentlist.add(String.valueOf(categoryInfo.getId()));
                contentlist.add(categoryInfo.getName());
                contentlist.add(String.valueOf(categoryInfo.getPid()));
                contentlist.add(String.valueOf(categoryInfo.getLevel()));
                contentlist.add(String.valueOf(categoryInfo.getSort()));
                contentlist.add(String.valueOf(categoryInfo.getStatus()));
                String[] arr = contentlist.toArray(new String[contentlist.size()]);
                csv.writeRecord(arr);
                result++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            csv.close();
        }
        return HttpResult.success(result);
    }

    //下载文件
    @GetMapping("/downloadCsv")
    public void downloadCsv(HttpServletResponse response) throws IOException {
        String fileName = "商品分类.csv";
        String realpath = "/Users/liutao/Downloads";
        File file = new File(realpath, fileName);
        if (file.exists()) {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8")); //显示中文

                byte[] buffer = new byte[1024];
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("download file success");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            }
        }
    }

    //上传文件
    @PostMapping("/upload")
    public HttpResult upload(@RequestParam("file") MultipartFile multipartFile) {
        String result = null;
        String directory = "/Users/liutao/Downloads";
        try {
            File file = FileUtil.toFile(multipartFile, directory);
            boolean b = file.createNewFile();
            System.out.println(file.getName());
            result = file.getName();
        } catch (IOException e) {
            e.printStackTrace();
            result = "服务异常";
        }
        return HttpResult.success(result);
    }
}
