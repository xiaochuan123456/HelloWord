package com.itmk.system.role_permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.system.role_permission.entity.RolePermission;

import java.util.List;

/**
 * @author xlc
 */
public interface RolePermissionService extends IService<RolePermission> {
    /**
     * 分配权限保存
     * @param roleId
     * @param collect
     */
    void saveAssignRole(Long roleId, List<Long> collect);
}
