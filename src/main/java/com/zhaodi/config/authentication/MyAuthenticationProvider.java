package com.zhaodi.config.authentication;

import com.zhaodi.config.authentication.service.MyUserDetailsService;
import com.zhaodi.custom.service.iml.BasicUserServiceIml;
import com.zhaodi.oa.service.HrmService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/4/8 18:20
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Resource(name="HrmService")
    private HrmService hrmService;

    @Resource(name="UserServiceIml")
    private BasicUserServiceIml userService;

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ApplicationEventPublisher publisher;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        /*System.out.println("客户端密码："+password);*/
        //获取用户信息
        UserDetails user = myUserDetailsService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = null;
        if(user.getPassword()!=null){
            if(StringUtils.isBlank(username)){
                throw new UsernameNotFoundException("username用户名不可以为空");
            }
            if(StringUtils.isBlank(password)){
                throw new BadCredentialsException("密码不可以为空");
            }
            //比较前端传入的密码明文和数据库中加密的密码是否相等
            /*System.out.println("数据库加密密码："+user.getPassword());*/
            if (!passwordEncoder.matches(password, user.getPassword())) {
                //发布密码不正确事件
                //publisher.publishEvent(new UserLoginFailedEvent(authentication));
                /*System.out.println("密码不一样");*/
                throw new BadCredentialsException("password密码不正确");
            }/*else{
                System.out.println("密码一样");
            }*/
            //获取用户权限信息
            authorities = user.getAuthorities();
            return new UsernamePasswordAuthenticationToken(user, password, authorities);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);

    }
}
