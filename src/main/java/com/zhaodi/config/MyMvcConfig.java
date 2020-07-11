package com.zhaodi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Locale;
//如果你想diy- 些定制化的功能，只要写这个组件，然后将它交给springboot, springboot就会帮我们自动装配!
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/index").setViewName("index");
    }
}
