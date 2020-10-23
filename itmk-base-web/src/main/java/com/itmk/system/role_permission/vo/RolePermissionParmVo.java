package com.itmk.system.role_permission.vo;

import com.itmk.system.permission.vo.TreeVo;
import lombok.Data;

import java.util.List;

/**
 * @author xlc
 */

@Data
public class RolePermissionParmVo {
    private List<TreeVo> list;
    private Long roleId;

}
