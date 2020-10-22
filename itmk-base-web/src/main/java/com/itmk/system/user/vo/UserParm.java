package com.itmk.system.user.vo;

import com.itmk.vo.ParmVo;
import lombok.Data;

@Data
public class UserParm extends ParmVo {
    private String userName;
    private String mobile;
    private String deptId;
    private String userEmail;
    private String userPhone;
}
