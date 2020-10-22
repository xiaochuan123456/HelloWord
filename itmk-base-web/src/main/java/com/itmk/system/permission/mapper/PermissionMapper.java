package com.itmk.system.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.system.permission.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限接口
 * @author xlc
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户id查询用户权限
     * @param userId
     * @return
     */
    List<Permission> getPermissionListByUserId(@Param("userId") Long userId);

    /**
     * 根据角色id查询权限列表
     * @param roleId
     * @return
     */
    List<Permission> getPermissionListByRoleId(@Param("roleId") Long roleId);
}
