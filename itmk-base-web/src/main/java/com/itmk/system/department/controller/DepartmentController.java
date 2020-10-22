package com.itmk.system.department.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.result.ResultUtils;
import com.itmk.result.ResultVo;
import com.itmk.system.department.entity.Department;
import com.itmk.system.department.service.DepartmentService;
import com.itmk.system.department.vo.DepartmentVo;
import com.itmk.utils.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 新增部门
     * @param department
     * @return
     */
    @PostMapping(value = "/addDepartment")
    public ResultVo addDepartment(@RequestBody Department department){
        String id = UUIDUtil.getUniqueIdByUUId();
        department.setId(id);
        boolean b = departmentService.save(department);
        if(b){
            return ResultUtils.success("新增部门成功!");
        }else{
            return ResultUtils.error("新增部门失败!");
        }
    }

    /**
     * 获取部门列表
     * @param departmentVo
     * @return
     */
    @PostMapping(value = "/getDepartmentList")
    public ResultVo getDepartmentList(@RequestBody DepartmentVo departmentVo){
        //1.生成sql条件构造器
        QueryWrapper<Department> query = new QueryWrapper<>();
        if(StringUtils.isNotBlank(departmentVo.getName())){
            query.lambda().like(Department::getName,departmentVo.getName());
        }
        if(StringUtils.isNotBlank(departmentVo.getPhone())){
            query.lambda().like(Department::getDeptPhone,departmentVo.getPhone());
        }
        query.lambda().eq(Department::getPid, departmentVo.getParentDeptId());

        //2.设置分页数据
        IPage<Department> page = new Page<>();
        page.setCurrent(departmentVo.getCurrentPage());
        page.setSize(departmentVo.getPageSize());
        IPage<Department> respage = departmentService.page(page, query);
        return ResultUtils.success("查询成功",respage);
    }

    /**
     * 根据id查询部门数据
     * @param department
     * @return
     */
    @PostMapping(value = "/getDepartmentById")
    public ResultVo getDepartmentById(@RequestBody Department department){
        Department res = departmentService.getById(department.getId());
        return ResultUtils.success("查询成功",res);
    }

    /**
     * 编辑部门保存
     * @param department
     * @return
     */
    @PostMapping(value = "/updateDepartmentById")
    public ResultVo updateDepartmentById(@RequestBody Department department){
        boolean b = departmentService.save(department);
        if(b){
            return ResultUtils.success("编辑部门成功!");
        }else{
            return ResultUtils.error("编辑部门失败!");
        }
    }

    /**
     * 根据id删除部门
     * @return
     */
    @DeleteMapping(value = "deleteDepartmentById")
    public ResultVo deleteDepartmentById(@RequestBody Department department){
        boolean b = departmentService.removeById(department.getId());
        if(b){
            return ResultUtils.success("删除部门成功!");
        }else{
            return ResultUtils.error("删除部门失败!");
        }
    }

    /**
     * 获取左侧部门树
     * @return
     */
    @PostMapping(value = "/getLeftDeptTree")
    public ResultVo getLeftDeptTree(){
        List<Department> list = departmentService.list();
        return  ResultUtils.success("成功",list);
    }

    /**
     * 新增部门中的获取上级部门树
     * @return
     */
    @PostMapping(value = "/getParentTree")
    public ResultVo getParentTree(){
        //获取列表
        List<Department> list = departmentService.list();
        Department department = new Department();
        department.setId("0");
        department.setPid("-1");
        department.setName("顶级部门");
        department.setLikeId("0,");
        list.add(0,department);
        return ResultUtils.success("成功",list);
    }
}
