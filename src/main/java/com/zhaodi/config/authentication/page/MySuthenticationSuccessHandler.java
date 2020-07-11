package com.zhaodi.config.authentication.page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaodi.bean.JsonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version 1.0
 * @Describe 成功访问
 * @Author WangBo
 * @Date 2020/4/5 16:00
 */
@Component
public class MySuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
    @Value("${spring.security.loginType}")
    private String loginType;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws ServletException, IOException {

        if(loginType.equalsIgnoreCase("JSON")){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new JsonResult<>(authentication,"1","/")));
        }else{
            //跳转到之前请求的页面
            super.onAuthenticationSuccess(request,response,authentication);
        }
    }
}
