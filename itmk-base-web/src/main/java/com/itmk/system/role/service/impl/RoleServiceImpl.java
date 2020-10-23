package com.itmk.system.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.role.entity.SysRole;
import com.itmk.system.role.mapper.RoleMapper;
import com.itmk.system.role.service.RoleService;
import com.itmk.system.user_role.entity.UserRole;
import com.itmk.system.user_role.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author xlc
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements RoleService {
    @Resource
    private UserRoleMapper userRoleMapper;
    @Override
    public UserRole getRoleIdByUserId(Long userId) {
        return userRoleMapper.getRoleIdByUserId(userId);
    }

    @Override
    @Transactional
    public void assignRole(UserRole userRole) {
        //先删除该用户原来的角色
        QueryWrapper<UserRole> query = new QueryWrapper<>();
        query.lambda().eq(UserRole::getUserId, userRole.getUserId()).eq(UserRole::getRoleId, userRole.getRoleId());
        userRoleMapper.delete(query);

        userRoleMapper.insert(userRole);

    }
}
