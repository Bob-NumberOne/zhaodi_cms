package com.zhaodi.config.authentication;
import com.zhaodi.config.authentication.page.MyAccessDeniedHandler;
import com.zhaodi.config.authentication.page.MyAuthenticationFailurelHandler;
import com.zhaodi.config.authentication.page.MySuthenticationSuccessHandler;
import com.zhaodi.config.authentication.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Describe spring security配置
 * @Author WangBo
 * @Date 2020/4/5 12:32
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    MySuthenticationSuccessHandler mySuthenticationSuccessHandler;
    @Resource
    MyAuthenticationFailurelHandler myAuthenticationFailurelHandler;
    @Resource
    MyUserDetailsService myUserDetailsService;
    @Resource
    MyAccessDeniedHandler myAccessDeniedHandler;
    /**
     * @Describe 登录认证 哪些角色访问哪些资源
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//跨站访问
        .formLogin()
            .loginPage("/layuiAdmin/page/login.html")//默认登录页面
            .loginProcessingUrl("/login")//登录控制层
            //.defaultSuccessUrl("/index")//登录成功后进入的页面
            //.failureUrl("/layuiAdmin/page/login.html")
            .successHandler(mySuthenticationSuccessHandler)
            .failureHandler(myAuthenticationFailurelHandler)
        .and()
        .authorizeRequests()
            .antMatchers("/layuiAdmin/page/login.html","/1ogin","/static/layuiAdmin/**").permitAll()//登录页授予权限
            .antMatchers("/index").authenticated()
            /*.antMatchers("/**") //需要对外暴露的资源路径
                .hasAnyAuthority("ROLE_user","ROLE_1001") // user角色和admin角色
            .antMatchers("/**")
                .hasAnyRole("ROLE_1001") //admin角色可以访问
            .anyRequest().authenticated()*/
            .anyRequest().access("@rbacService.hasPermission(request,authentication)")
        .and().exceptionHandling()//配置被拦截时的处理  这个位置很关键
            .accessDeniedHandler(myAccessDeniedHandler)//添加无权限时的处理
        .and().headers().frameOptions().disable()//允许iframe中打开
        .and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .invalidSessionUrl("/layuiAdmin/page/login.html")//session失效跳转到登录页面
            .sessionFixation().migrateSession()//每次登录重新生成sessionID
            .maximumSessions(1)//最多session数量（用户在线数量）
            .maxSessionsPreventsLogin(false)//是否保留已经登录的用户；为true，新用户无法登录；为 false，旧用户被踢出
            .expiredSessionStrategy(new MyExpiredSessionStrategy());//session失效后回调函数，旧用户被踢出后处理方法
    }
    /**
     * @Describe 加载配置的用户
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
/*        auth.inMemoryAuthentication()
        .withUser("user")
        .password(passwordEncoder().encode("123456"))
        .roles("user")
            .and()
        .withUser("admin")
        .password(passwordEncoder().encode("123456"))
        //. authorities( "sys: log”， sys:user”)
        .roles("admin")
            .and()
        .passwordEncoder (passwordEncoder()) ;//配置BCrypt加密*/
        auth/*.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder())
            .and()*/
            //添加自定义的认证管理类
            .authenticationProvider(smsAuthenticationProvider());
    }
    @Bean
    public AuthenticationProvider smsAuthenticationProvider(){
        AuthenticationProvider authenticationProvider = new MyAuthenticationProvider();
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();
    }
    /**
     * @Describe 将项目中静态资源路径开放出来
     */
    @Override
    public void configure (WebSecurity web) {
        web.ignoring().antMatchers("/layuiAdmin/**"
                ,"/echarts/**"
                ,"/css/**"
                ,"/img/**"
                ,"/js/**"
                ,"/file/**"
                ,"/ecology80/**"
                ,"/basic/getTree.xmj"
                ,"/page/*.do"
                ,"/project/*.xmj");
    }
}