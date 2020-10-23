package com.itmk.system.role.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.result.ResultUtils;
import com.itmk.result.ResultVo;
import com.itmk.system.permission.entity.Permission;
import com.itmk.system.permission.service.PermissionService;
import com.itmk.system.permission.vo.TreeVo;
import com.itmk.system.role.entity.SysRole;
import com.itmk.system.role.service.RoleService;
import com.itmk.system.role.vo.ParmVo;
import com.itmk.system.role.vo.RoleParm;
import com.itmk.system.role_permission.service.RolePermissionService;
import com.itmk.system.role_permission.vo.RolePermissionParmVo;
import com.itmk.system.user.entity.SysUser;
import com.itmk.system.user.service.UserService;
import com.itmk.system.user_role.entity.UserRole;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xlc
 */
@RestController
@RequestMapping(value = "/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @PostMapping(value = "addRole")
    public ResultVo addRole(@RequestBody SysRole role) {
        boolean b = roleService.save(role);
        if (b) {
            return ResultUtils.success("新增角色成功成功!");
        } else {
            return ResultUtils.error("新增角色失败！");
        }
    }

    /**
     * 根据id查询角色
     *
     * @return
     */
    @PostMapping(value = "/getRoleById")
    public ResultVo getRoleById(@RequestBody SysRole sysRole) {
        SysRole role = roleService.getById(sysRole.getId());
        return ResultUtils.success("查询角色成功", role);

    }

    /**
     * 编辑角色
     *
     * @return
     */
    @PostMapping(value = "/updateRole")
    public ResultVo updateRole(@RequestBody SysRole sysRole) {
        boolean b = roleService.updateById(sysRole);
        if (b) {
            return ResultUtils.success("编辑角色成功!");
        } else {
            return ResultUtils.error("编辑角色失败!");
        }
    }

    /**
     * 删除角色
     *
     * @return
     */
    @PostMapping(value = "/deleteRole")
    public ResultVo deleteRole(@RequestBody SysRole sysRole) {
        boolean b = roleService.removeById(sysRole.getId());
        if (b) {
            return ResultUtils.success("删除角色成功!");
        } else {
            return ResultUtils.error("删除角色失败!");
        }

    }

    /**
     * 查询角色列表
     *
     * @param roleParm
     * @return
     */
    @PostMapping("/getRoleList")
    public ResultVo getRoleList(@RequestBody RoleParm roleParm) {
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        if (!StringUtils.isBlank(roleParm.getTitle())) {
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
     *
     * @return
     */
    @PostMapping("/getRoleListForAssign")
    public ResultVo getRoleListForAssign() {
        List<SysRole> list = roleService.list();
        return ResultUtils.success("成功", list);
    }

    /**
     * 根据用户id查询角色id
     *
     * @return
     */
    @PostMapping("/getRoleIdByUserId")
    public ResultVo getRoleIdByUserId(@RequestBody UserRole userRole) {
        UserRole RoleId = roleService.getRoleIdByUserId(userRole.getUserId());
        return ResultUtils.success("查询成功", RoleId);
    }

    /**
     * 分配用户的角色
     *
     * @param userRole
     * @return
     */
    @RequestMapping(value = "/assignRole", method = RequestMethod.POST)
    public ResultVo assignRole(@RequestBody UserRole userRole) {
        roleService.assignRole(userRole);

        return ResultUtils.success("分配角色成功!");

    }

    /**
     * 分配权限树查询
     *
     * @return
     */
    @RequestMapping(value = "/permissonTree", method = RequestMethod.POST)
    public ResultVo permissonTree(@RequestBody ParmVo parmVo) {
        SysUser sysUser = userService.getById(parmVo.getUserId());

        //1.查询当前用户的所有权限
        Long userId = parmVo.getUserId();
        List<Permission> permissions = null;
        if (StringUtils.isNotEmpty(sysUser.getIsAdmin()) && sysUser.getIsAdmin().equals("1")) {
            permissions = permissionService.list();
        } else {
            permissions = permissionService.getPermissionListByUserId(userId);
        }
        //2.根据当前用户的角色id查询该角色的拥有权限
        List<Permission> RolePermissions = permissionService.getPermissionListByRoleId(parmVo.getRoleId());
        //3.把2中的数据设为选中
        List<TreeVo> listTree = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++) {
            if (permissions.get(i) != null) {
                TreeVo tree = new TreeVo();
                tree.setId(permissions.get(i).getId());
                tree.setName(permissions.get(i).getLabel());
                tree.setPid(permissions.get(i).getParentId());
                if (RolePermissions.size() > 0) {
                    for (int j = 0; j < RolePermissions.size(); j++) {
                        if (permissions.get(i).getId().equals(RolePermissions.get(j).getId())) {
                            tree.setChecked(true);
                            break;
                        }
                    }
                }
                listTree.add(tree);
            }
        }
        return ResultUtils.success("查询成功", listTree);
    }

    /**
     * 保存分配的权限
     * @param parmVo
     * @return
     */
    @RequestMapping(value = "/saveAssignRole", method = RequestMethod.POST)
    public ResultVo saveAssignRole(@RequestBody RolePermissionParmVo parmVo) {
        if (parmVo != null && !parmVo.getList().isEmpty()) {
            List<TreeVo> list = parmVo.getList();
            Long roleId = parmVo.getRoleId();
            List<Long> ids = list.stream().filter(item -> item != null).map(item -> item.getId()).collect(Collectors.toList());
            rolePermissionService.saveAssignRole(roleId, ids);
            return ResultUtils.success("分配成功!");
        } else {
            return ResultUtils.error("请选择权限!");
        }
    }

}
