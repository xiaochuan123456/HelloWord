package com.itmk.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.user.entity.SysUser;

import com.itmk.system.user.mapper.UserMapper;
import com.itmk.system.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户Service实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,SysUser> implements UserService {

    @Override
    public SysUser getUserByUserName(String username) {
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername,username);
        return this.baseMapper.selectOne(query);
    }
}
