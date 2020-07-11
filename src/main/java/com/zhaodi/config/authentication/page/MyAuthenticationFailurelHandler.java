package com.zhaodi.config.authentication.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaodi.bean.JsonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version 1.0
 * @Describe 访问失败
 * @Author WangBo
 * @Date 2020/4/5 16:45
 */
@Component
public class MyAuthenticationFailurelHandler extends SimpleUrlAuthenticationFailureHandler {
    @Value("${spring.security.loginType}")
    private String loginType;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws ServletException, IOException {

        if(loginType.equalsIgnoreCase("JSON")){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new JsonResult<>("0","用户名或密码错误！")));
        }else{
            //跳转到登录页面
            super.onAuthenticationFailure(request,response,exception);
        }
    }
}
