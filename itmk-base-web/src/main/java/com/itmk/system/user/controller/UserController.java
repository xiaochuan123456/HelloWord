package com.itmk.system.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.result.ResultUtils;
import com.itmk.result.ResultVo;
import com.itmk.system.user.entity.SysUser;
import com.itmk.system.user.service.UserService;
import com.itmk.system.user.vo.UserParm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    public static final String SESSION_KEY = "IMAGE_CODE";
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 获取用户信息列表
     *
     * @return
     */
    @PostMapping("/getUserList")
    public ResultVo getUserList(@RequestBody UserParm parm) {
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        if (StringUtils.isNotBlank(parm.getUserName())) {
            query.lambda().eq(SysUser::getLoginName, parm.getUserName());
        }
        if (StringUtils.isNotBlank(parm.getMobile())) {
            query.lambda().eq(SysUser::getMobile, parm.getMobile());
        }
        if (StringUtils.isNotBlank(parm.getUserEmail())) {
            query.lambda().eq(SysUser::getEmail, parm.getUserEmail());
        }
        query.lambda().eq(SysUser::getDeptId, parm.getDeptId());
        IPage<SysUser> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<SysUser> userIPage = userService.page(page, query);
        return ResultUtils.success("查询用户列表成功", userIPage);
    }

    /**
     * 新增用户
     *
     * @return
     */
    @PostMapping("/addUser")
    public ResultVo addUser(@RequestBody SysUser user) {
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, user.getUsername());
        //查询用户是否存在
        SysUser one = userService.getOne(query);
        if (one != null) {
            return ResultUtils.error("用户名已经存在!");
        }
        //加密用户密码
        String pwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(pwd);
        boolean b = userService.save(user);
        if (b) {
            return ResultUtils.success("新增用户成功");
        } else {
            return ResultUtils.error("新增用户失败");
        }
    }

    /**
     * 编辑用户保存
     *
     * @return
     */
    @PostMapping("/updateSaveUser")
    public ResultVo updateSaveUser(@RequestBody SysUser user) {
        //判断用户是否存在
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, user.getUsername());
        SysUser one = userService.getOne(query);
        //查询出来的id
        Long id = one.getId();
        //编辑的用户id
        Long editId = user.getId();
        if (!id.equals(editId)) {
            return ResultUtils.error("用户名已经存在!");
        }
        boolean b = userService.updateById(user);
        if (b) {
            return ResultUtils.success("编辑成功");
        } else {
            return ResultUtils.error("编辑失败");
        }
    }

    /**
     * 根据用户id查询用户端
     * @return
     */
    @PostMapping("/getUserById")
    public ResultVo getUserById(@RequestBody SysUser user){
        SysUser sysUser = userService.getById(user.getId());
        return ResultUtils.success("查询成功",sysUser);
    }

    /**
     * 根据用户id删除
     *
     * @return
     */
    @DeleteMapping("/deleteUserById")
    public ResultVo deleteUserById(@RequestBody SysUser user) {
        boolean b = userService.removeById(user.getId());
        if (b) {
            return ResultUtils.success("删除用户成功");
        } else {
            return ResultUtils.error("删除用户失败");
        }
    }
}

