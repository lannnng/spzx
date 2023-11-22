package com.learn.interceptor;

import com.learn.config.InterceptorConfig;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RegisterInterceptor implements WebMvcConfigurer {
    @Resource
    MyLoginInterceptor myLoginInterceptor;
    //拿到配置类对象
    @Resource
    InterceptorConfig interceptorConfig;
    // 注册自定义的拦截器类，和排除不需要的拦截的路径和资源（通过配置文件读取的方式）
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(myLoginInterceptor).addPathPatterns("/**").excludePathPatterns(interceptorConfig.getUrls());
    }
    //添加全局的跨域配置 addCorsMapping
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //允许所有的路径
                .allowedOriginPatterns("*")   //允许所有的前端服务器访问
                .allowCredentials(true)       //允许携带cookie
                .allowedHeaders("*")          //允许所有的头
                .allowedMethods("*");         //允许所有请求方式
    }


}
