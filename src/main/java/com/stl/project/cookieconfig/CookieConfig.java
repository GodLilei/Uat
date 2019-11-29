package com.stl.project.cookieconfig;

import com.stl.project.cookieconfig.intercepors.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class CookieConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
//        registry.addResourceHandler("/layui/**").addResourceLocations("classpath:/static/layui/");
    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/Express/**").excludePathPatterns("/Express/fastLogin",
                "/Express/normalLogin");
    }
}
