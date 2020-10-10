package com.itmk.security.handle;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itmk.jwt.JwtUtils;
import com.itmk.result.ResultUtils;
import com.itmk.system.permission.entity.Permission;
import com.itmk.system.permission.vo.MakeMenuTree;
import com.itmk.system.permission.vo.MenuVo;
import com.itmk.system.user.entity.SysUser;
import com.itmk.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录成功处理器
 * 登录成功要返回json和token
 * @author xlc
 */
@Component("loginSuccessHandle")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //1.生成token
        SysUser user = (SysUser)authentication.getPrincipal();
        String token = jwtUtils.generateToken(user);
        MenuVo vo = new MenuVo();
        vo.setToken(token);
        vo.setUserId(user.getId());
        //2.获取用户的菜单权限
        List<Permission> permissionList = user.getPermissionList();
        //3.获取code字段，返回给前端使用,判断是否有按钮权限
        List<String> auth = permissionList.stream().filter(item -> item != null).map(item -> item.getCode()).collect(Collectors.toList());
        vo.setAuthList(auth);
        //4.生成菜单数据树
        List<Permission> permissions = permissionList.stream().filter(item -> item !=null && !item.getType().equals("2")).collect(Collectors.toList())
        List<Permission> listMenu = MakeMenuTree.makeTree(permissions, 0L  );
        vo.setMenuList(listMenu);
        //5.查询路由的url
        List<Permission> routerList = permissionList.stream().filter(item -> item != null && item.getType().equals("1")).collect(Collectors.toList());
        vo.setRouterList(routerList);

        String res = JSONObject.toJSONString(ResultUtils.success("认证成功", vo), SerializerFeature.DisableCircularReferenceDetect);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = httpServletResponse.getOutputStream();
        out.write(res.getBytes("UTF-8"));
        out.flush();
        out.close();

    }
}
