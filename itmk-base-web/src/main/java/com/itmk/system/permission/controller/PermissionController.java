package com.itmk.system.permission.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itmk.result.ResultUtils;
import com.itmk.result.ResultVo;
import com.itmk.status.StatusCode;
import com.itmk.system.permission.entity.Permission;
import com.itmk.system.permission.service.PermissionService;
import com.itmk.system.permission.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.itmk.system.permission.vo.MakeMenuTree.makeTree;

@RestController
@RequestMapping(value = "/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 查询权限列表
     *
     * @return ResultVo
     */
    @PostMapping("/getMenuList")
    public ResultVo getMenuList() {
        QueryWrapper<Permission> query = new QueryWrapper<>();
        query.lambda().orderByAsc(Permission::getOrderNum);
        List<Permission> list = permissionService.list(query);
        List<Permission> menuList = null;
        if (!list.isEmpty()) {
            menuList = makeTree(list, 0L);
        }
        return ResultUtils.success("成功", StatusCode.SUCCESS_CODE, menuList);
    }

    /**
     * 新增权限
     */
    @PostMapping(value = "/addPermission")
    public ResultVo addPermission(@RequestBody Permission permission) {
        boolean b = permissionService.save(permission);
        if (b) {
            return ResultUtils.success("新增权限成功");
        } else {
            return ResultUtils.error("新增权限失败");
        }
    }

    /**
     * 根据id查询权限
     * @param permission
     * @return
     */
    @GetMapping(value = "/getPermissionById")
    public ResultVo getMenuById(@RequestBody Permission permission){
        Permission menu = permissionService.getById(permission.getId());
        return ResultUtils.success("成功",menu);
    }

    /**
     * 根据id更新权限
     * @param permission
     * @return
     */
    @PostMapping(value = "/editPermission")
    public ResultVo editPermission(@RequestBody Permission permission){
        permission.setCreateTime(new Date());
        boolean res = permissionService.updateById(permission);
        if(res){
            return ResultUtils.success("更新成功");
        }else{
            return ResultUtils.error("更新失败");
        }

    }

    /**
     * 删除权限
     * @return
     */
    @DeleteMapping(value = "/deleteById")
    public ResultVo deleteById(@RequestBody Permission permission){

        boolean b = permissionService.removeById(permission.getId());
        if(b){
            return ResultUtils.success("删除成功!");
        }else{
            return ResultUtils.error("删除失败!");
        }

    }
    /**
     * 获取上级菜单树
     * @return
     */
    @PostMapping(value = "/getParentTree")
    public ResultVo getParentTree(){
        QueryWrapper<Permission> query = new QueryWrapper<>();
        query.lambda().eq(Permission::getType,"0").or().eq(Permission::getType,"1");
        List<Permission> list = permissionService.list(query);
        List<TreeVo> listTree = new ArrayList<>();
        TreeVo parentTree = new TreeVo();
        parentTree.setId(0L);
        parentTree.setPid(-1L);
        parentTree.setName("顶级菜单");
        parentTree.setOpen(true);
        parentTree.setChecked(false);
        listTree.add(parentTree);
        if(list.size() > 0){
            for(Permission p : list){
                if(p != null){
                    TreeVo tree = new TreeVo();
                    tree.setId(p.getId());
                    tree.setPid(p.getParentId());
                    tree.setName(p.getLabel());
                    tree.setOpen(true);
                    tree.setChecked(false);
                    listTree.add(tree);
                }
            }
        }

        return ResultUtils.success("上级菜单树查询成功", listTree);
    }
}
