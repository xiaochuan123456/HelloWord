package com.itmk.system.role.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.result.ResultUtils;
import com.itmk.result.ResultVo;
import com.itmk.system.role.entity.SysRole;
import com.itmk.system.role.service.RoleService;
import com.itmk.system.role.vo.RoleParm;
import com.itmk.system.user_role.entity.UserRole;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xlc
 */
@RestController
@RequestMapping(value = "/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping(value = "addRole")
    public ResultVo addRole(@RequestBody SysRole role){
        boolean b = roleService.save(role);
        if(b){
            return ResultUtils.success("新增角色成功成功!");
        }else{
            return ResultUtils.error("新增角色失败！");
        }
    }

    /**
     * 根据id查询角色
     * @return
     */
    @PostMapping(value = "/getRoleById")
    public ResultVo getRoleById(@RequestBody SysRole sysRole){
        SysRole role = roleService.getById(sysRole.getId());
        return ResultUtils.success("查询角色成功",role);

    }

    /**
     * 编辑角色
     * @return
     */
    @PostMapping(value = "/updateRole")
    public ResultVo updateRole(@RequestBody SysRole sysRole){
        boolean b = roleService.updateById(sysRole);
        if(b){
            return ResultUtils.success("编辑角色成功!");
        }else{
            return ResultUtils.error("编辑角色失败!");
        }
    }

    /**
     * 删除角色
     * @return
     */
    @PostMapping(value = "/deleteRole")
    public ResultVo deleteRole(@RequestBody SysRole sysRole){
        boolean b = roleService.removeById(sysRole.getId());
        if(b){
            return ResultUtils.success("删除角色成功!");
        }else{
            return ResultUtils.error("删除角色失败!");
        }

    }

    /**
     * 查询角色列表
     * @param roleParm
     * @return
     */
    @PostMapping("/getRoleList")
    public ResultVo getRoleList(@RequestBody RoleParm roleParm) {
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        if(!StringUtils.isBlank(roleParm.getTitle())){
            query.lambda().like(SysRole::getName, roleParm.getTitle());
        }

        IPage<SysRole> page = new Page<>();
        page.setSize(roleParm.getPageSize());
        page.setCurrent(roleParm.getCurrentPage());
        IPage<SysRole> roleList = roleService.page(page, query);
        return ResultUtils.success("查询成功", roleList);
    }

    /**
     * 分配角色时查询角色列表
     * @return
     */
    @PostMapping("/getRoleListForAssign")
    public ResultVo getRoleListForAssign(){
        List<SysRole> list = roleService.list();
        return ResultUtils.success("成功",list);
    }

    /**
     * 根据用户id查询角色id
     * @return
     */
    @PostMapping("/getRoleIdByUserId")
    public ResultVo getRoleIdByUserId(@RequestBody UserRole userRole){
        UserRole RoleId = roleService.getRoleIdByUserId(userRole.getUserId());
        return ResultUtils.success("查询成功", RoleId);
    }

    /**
     * 分配用户的角色
     * @param userRole
     * @return
     */
    @RequestMapping(value = "/assignRole",method = RequestMethod.POST)
    public ResultVo assignRole(@RequestBody UserRole userRole){
      roleService.assignRole(userRole);

            return ResultUtils.success("分配角色成功!");

    }

}
