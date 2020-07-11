package com.zhaodi.config.authentication.service;

import com.zhaodi.custom.service.iml.BasicUserServiceIml;
import com.zhaodi.oa.entity.HrmResource;
import com.zhaodi.oa.service.HrmService;
import jdk.nashorn.internal.objects.NativeString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 动态权限
 * @date 2020/4/9 10:17
 */
@Component("rbacService")
public class MyRBACService {
    /**
     * @Describe 判断某用户是否具有该request资源的访问权限
     * @author WangBo
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Resource(name="HrmService")
    private HrmService hrmService;

    @Resource(name="UserServiceIml")
    private BasicUserServiceIml userService;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication){
        Object principal = authentication. getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            HrmResource userInfo = hrmService.getHrmResource(username);
            if(userInfo!=null) {
                List<String> urls = userService.findResourcesByName(userInfo.getId());
                //给定登录者默认首页权限
                urls.add("/");
                urls.add("/index");
                urls.add("/index.html");
                return urls.stream().anyMatch(
                        url -> antPathMatcher.match(url, request.getRequestURI())
                );
            }
        }
        return false;
    }
}
