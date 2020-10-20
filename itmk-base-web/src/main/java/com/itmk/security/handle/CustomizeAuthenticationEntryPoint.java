package com.itmk.security.handle;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itmk.result.ResultUtils;
import com.itmk.status.StatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户访问无权限资源时的异常
 * @author xlc
 */
@Component("customizeAuthenticationEntryPoint")
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ServletOutputStream out = httpServletResponse.getOutputStream();
        String res = JSONObject.toJSONString(ResultUtils.error("匿名用户无权限访问,请登录！", StatusCode.NO_LOGIN,null), SerializerFeature.DisableCircularReferenceDetect);
        out.write(res.getBytes("UTF-8"));
        out.flush();
        out.close();

    }
}
