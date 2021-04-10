package com.example.impl;

import com.example.mappers.ShopUserMapper;
import com.example.pojo.ShopUser;
import com.example.service.ShopUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ShopUserServiceImpl implements ShopUserService {
    @Autowired
    private ShopUserMapper shopUserMapper;

    @Override
    public List<ShopUser> list() {
        return shopUserMapper.selectAll();
    }

    @Override
    public PageInfo<ShopUser> pageList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<ShopUser> list = shopUserMapper.selectAll();
        PageInfo<ShopUser> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public ShopUser find(String id) {
        ShopUser shopUser = shopUserMapper.selectByPrimaryKey(id);

        //Example example = new Example(ShopUser.class);
        //example.createCriteria().andEqualTo("id", id);
        //UserInfo shopUser = userInfoMapper.selectOneByExample(example);
        //return shopUser;
        return shopUserMapper.find(id);
    }

    @Override
    public ShopUser findByName(String name) {
        //return shopUserMapper.findByName(name);

        Example example = new Example(ShopUser.class);
        example.createCriteria().andEqualTo("name", name);
        ShopUser user = shopUserMapper.selectOneByExample(example);
        return user;
    }

    @Override
    public int add(ShopUser user) {
        return shopUserMapper.insertSelective(user);
    }

    @Override
    public int save(ShopUser shopUser) {
        return shopUserMapper.updateByPrimaryKeySelective(shopUser);
    }

    @Override
    public int delete(Long id) {
        return shopUserMapper.deleteByPrimaryKey(id);
    }
}
