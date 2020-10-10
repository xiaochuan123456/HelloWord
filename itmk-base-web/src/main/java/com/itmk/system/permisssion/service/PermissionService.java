package com.itmk.system.permisssion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.system.permisssion.entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {

    /**
     * 根据用户id查询所有的权限
     * @param userId
     * @return
     */
    List<Permission> selectPermissionByUserId(Long userId);

    /**
     * 根据角色id查询所有的权限
     * @param roleId
     * @return
     */
    List<Permission> findByRoleId(Long roleId);

}
