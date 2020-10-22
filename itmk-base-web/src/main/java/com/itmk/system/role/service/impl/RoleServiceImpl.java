package com.itmk.system.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.role.entity.SysRole;
import com.itmk.system.role.mapper.RoleMapper;
import com.itmk.system.role.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author xlc
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements RoleService {
}
