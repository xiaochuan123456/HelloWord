package com.itmk.system.user.controller;

import com.itmk.result.ResultUtils;
import com.itmk.result.ResultVo;
import com.itmk.system.user.entity.SysUser;
import com.itmk.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/system/user")
public class UserController {

    public static final String SESSION_KEY = "IMAGE_CODE";
    @Autowired
    private UserService userService;


    /**
     * 获取用户信息列表
     * @return
     */
    @PostMapping("/getList")
    public ResultVo getList() {
        List<SysUser> list = userService.list();
        return ResultUtils.success("查询成功", list);
    }
}

