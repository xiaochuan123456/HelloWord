package com.itmk.system.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.system.role.entity.SysRole;
import com.itmk.system.user_role.entity.UserRole;

/**
 * @author xlc
 */
public interface RoleService extends IService<SysRole> {
    /**
     * 根据用户id查询角色id
     *
     * @return
     */
    UserRole getRoleIdByUserId(Long userId);

    /**
     * 分配权限
     *
     * @param userRole
     * @return
     */
    void assignRole(UserRole userRole);
}
