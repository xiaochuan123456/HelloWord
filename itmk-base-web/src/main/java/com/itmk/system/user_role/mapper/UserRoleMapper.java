package com.itmk.system.user_role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.system.user_role.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author xlc
 */
@Component
public interface UserRoleMapper extends BaseMapper<UserRole> {
    UserRole getRoleIdByUserId(@Param("userId") Long userId);
}
