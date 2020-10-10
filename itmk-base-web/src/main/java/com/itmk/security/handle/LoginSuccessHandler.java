package com.itmk.security.handle;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itmk.jwt.JwtUtils;
import com.itmk.result.ResultUtils;
import com.itmk.system.user.entity.SysUser;
import com.itmk.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        //生成token
        SysUser user = (SysUser)authentication.getPrincipal();
        String token = jwtUtils.generateToken(user);
        MenuVo menuVo = new MenuVo();
        menuVo.setToken(token);
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        String res = JSONObject.toJSONString(ResultUtils.success("认证成功",menuVo), SerializerFeature.DisableCircularReferenceDetect);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = httpServletResponse.getOutputStream();
        out.write(res.getBytes("UTF-8"));
        out.flush();
        out.close();

    }
}
