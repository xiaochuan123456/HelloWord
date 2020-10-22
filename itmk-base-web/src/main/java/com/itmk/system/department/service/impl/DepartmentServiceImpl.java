package com.itmk.system.department.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.department.entity.Department;
import com.itmk.system.department.mapper.DepartmentMapper;
import com.itmk.system.department.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
}

