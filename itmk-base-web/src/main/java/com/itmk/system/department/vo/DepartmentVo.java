package com.itmk.system.department.vo;

import com.itmk.vo.ParmVo;
import lombok.Data;

/**
 * @author Admin
 */
@Data
public class DepartmentVo extends ParmVo {
    private String name;
    private String phone;
    private String parentDeptId;
}
