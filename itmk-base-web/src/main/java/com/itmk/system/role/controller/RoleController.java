package com.itmk.system.role.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.result.ResultUtils;
import com.itmk.result.ResultVo;
import com.itmk.system.role.entity.SysRole;
import com.itmk.system.role.service.RoleService;
import com.itmk.system.role.vo.RoleParm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
