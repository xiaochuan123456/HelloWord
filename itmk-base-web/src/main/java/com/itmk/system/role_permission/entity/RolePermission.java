package com.itmk.system.role_permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xlc
 */
@Data
@TableName(value = "sys_role_permission")
public class RolePermission implements Serializable {
    @TableId(type= IdType.AUTO)
    private Long id;
    private Long roleId;
    private Long permissionId;
}
