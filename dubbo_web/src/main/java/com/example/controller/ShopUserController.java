package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.pojo.ShopUser;
import com.example.service.ShopUserService;
import com.example.tool.HttpResult;
import com.example.tool.UserLoginToken;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//jwt和swagger
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class ShopUserController {
    @Reference
    ShopUserService shopUserService;
    //@Reference
    //TokenService tokenService;

    //用户列表
    @GetMapping("/list")
    public List<ShopUser> list() {
        return shopUserService.list();
    }

    //用户列表分页
    @ApiOperation(value = "用户列表分页", notes = "接受分页参数pageNum,pageSize")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询用户成功"),
            @ApiResponse(code = 500, message = "条件分页查询用户失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    @GetMapping("/pageList")
    public HttpResult pageList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<ShopUser> list = shopUserService.pageList(pageNum, pageSize);
        return HttpResult.success(list);
    }

    //用户详情
    @ApiOperation(value = "用户详情", notes = "接收参数id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "用户id", required = true, dataType = "int"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询用户成功"),
            @ApiResponse(code = 500, message = "条件分页查询用户失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ShopUser find(String id) {
        return shopUserService.find(id);
    }

    //添加用户
    @PostMapping("/add")
    public int add(@RequestBody ShopUser user) {
        return shopUserService.add(user);
    }

    //更新用户
    @PutMapping("/save")
    public int save(@RequestBody ShopUser user) {
        return shopUserService.save(user);
    }

    //删除用户
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Long id) {
        return shopUserService.delete(id);
    }

    //登录
    @PostMapping("/login")
    public Object login(ShopUser user) {
        JSONObject jsonObject = new JSONObject();
        ShopUser userBase = shopUserService.findByName(user.getName());
        if (userBase == null) {
            jsonObject.put("message", "登录失败,用户不存在");
            return jsonObject;
        } else {
            if (!userBase.getPassword().equals(user.getPassword())) {
                jsonObject.put("message", "登录失败,密码错误");
                return jsonObject;
            } else {
                String token = null;
                //String token = tokenService.getToken(userBase);
                //jsonObject.put("token", token);
                jsonObject.put("user", userBase);
                return jsonObject;
            }
        }
    }

    //jwt getMessage
    @UserLoginToken //接口必须获取token，请求头加上token通过验证才能访问
    @GetMapping("/message")
    public String message() {
        return "jwt验证通过";
    }

    //用户权限
    @GetMapping(value = "/auth")
    public ResponseEntity<Object> getUser(String name) {
        //ShopUser userInfo = shopUserService.findByName(SecurityUtils.getName());
        ShopUser shopUser = shopUserService.findByName(name);
        return ResponseEntity.ok(shopUser);
    }
}
