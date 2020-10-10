package com.itmk.system.permisssion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.system.permisssion.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限接口
 * @author xlc
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户id查询用户权限
     * @param userId
     * @return
     */
    List<Permission> selectPermissionByUserId(@Param("userId") Long userId);

    /**
     * 根据角色id查询权限列表
     * @param roleId
     * @return
     */
    List<Permission> findByRoleId(@Param("roleID") Long roleId);
}
