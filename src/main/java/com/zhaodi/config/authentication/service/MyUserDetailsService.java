package com.zhaodi.config.authentication.service;

import com.zhaodi.config.authentication.MyUserDetails;
import com.zhaodi.custom.service.iml.BasicUserServiceIml;
import com.zhaodi.oa.entity.HrmResource;
import com.zhaodi.oa.service.HrmService;
import com.zhaodi.util.MD5Util;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Describe 加载用户相关信息
 * @Author WangBo
 * @Date 2020/4/6 17:18
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Resource(name="HrmService")
    private HrmService hrmService;

    @Resource(name="UserServiceIml")
    private BasicUserServiceIml userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        MyUserDetails userDetails=new MyUserDetails();
        userDetails.setUsername(userName);//用户名
        //1、加载用户基础信息
        HrmResource userInfo = hrmService.getHrmResource(userName);
        if(userInfo!=null){
            userDetails.setUserId(userInfo.getId());
            userDetails.setPassword(userInfo.getPassword());//密码
            userDetails.setLastName(userInfo.getLastname());//设置名字
    /*      status代表的含义：
            0：试用
            1：正式
            2：临时
            3：试用延期
            4：解聘
            5：离职
            6：退休
            7：无效*/
            if(userInfo.getStatus()==5 || userInfo.getStatus()==7){
                userDetails.setEnabled(false);
            }else{
                userDetails.setEnabled(true);
            }
            // 2、加载用户权限（角色）信息
            List<Long> roles=userService.findRoleByName(userInfo.getId());
            //角色是一个特殊的权限，ROLE前缀
            List<String> roless=new ArrayList<>();
            for(long role:roles){
                roless.add("ROLE_"+role);
            }
            roless.add("ROLE_default");

            //3、通过角色加载用户的资源权限列表
            List<String> authorities=userService.findResourcesByName(userInfo.getId());
            //给定登录者默认首页权限
            authorities.add("/");
            authorities.add("/index");
            authorities.add("/index.html");
            authorities.addAll(roless);//复制的对象的引用
            //System.out.println(authorities);

            userDetails.setAuthorities(
                    AuthorityUtils.commaSeparatedStringToAuthorityList(
                            String.join(",",authorities)
                    )
            );
        }
        return userDetails;
    }
}
