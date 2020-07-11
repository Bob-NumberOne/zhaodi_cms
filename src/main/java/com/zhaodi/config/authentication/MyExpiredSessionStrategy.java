package com.zhaodi.config.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaodi.bean.JsonResult;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @version 1.0
 * @Describe session失效处理
 * @Author WangBo
 * @Date 2020/4/6 11:58
 */
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    /**
     * @Describe session失效回调函数
     * @author WangBo
     * @return
     */
    private static ObjectMapper objectMapper=new ObjectMapper();
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException{
        sessionInformationExpiredEvent.getResponse().setContentType("application/json;charset=UTF-8");
        sessionInformationExpiredEvent
                .getResponse()
                .getWriter()
                .write(objectMapper.writeValueAsString(new JsonResult<>("0","另一台电脑或者浏览器登录，被迫下线！")));
    }
}
