package com.itmk.security.detailservice;

import com.itmk.system.permission.entity.Permission;
import com.itmk.system.permission.service.PermissionService;
import com.itmk.system.user.entity.SysUser;
import com.itmk.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义认证类
 * 查询数据库是否有用户
 */
@Component("CustomerUserDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PermissionService permissionService;
//    @Autowired
//    private CacheService cacheService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户名从数据库查询用户是否存在
        SysUser user = userService.getUserByUserName(username);
//        //缓存key
//        String userKey = KeyCode.USER_KEY+username;
//        SysUser user =  cacheService.getEntityCache(userKey,600000000L,SysUser.class,() -> userService.getUserByUserName(username));
        //2.用户不存在抛出异常
        if (null == user) {
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
        //3.查询用户所有的权限
        List<Permission> codeList = permissionService.getPermissionListByUserId(user.getId());

        //4.获取权限code字段
        List<String> collect = codeList.stream().filter(item -> item != null).map(item -> item.getCode()).collect(Collectors.toList());
        //5.把code交给Spring Security
        String[] strings = collect.toArray(new String[collect.size()]);
        //6.把code转成List<GrantedAuthority>
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(strings);
        user.setAuthorities(authorityList);
        //7.把菜单列表封装到user
        user.setPermissionList(codeList);
        return user;
    }
}

