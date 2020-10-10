package com.itmk.system.permisssion.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.permisssion.entity.Permission;
import com.itmk.system.permisssion.mapper.PermissionMapper;
import com.itmk.system.permisssion.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<Permission> selectPermissionByUserId(Long userId) {
        return this.baseMapper.selectPermissionByUserId(userId);
    }

    @Override
    public List<Permission> findByRoleId(Long roleId) {
        return this.baseMapper.findByRoleId(roleId);
    }
}
