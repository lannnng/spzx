package com.learn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

// 读取配置文件 存到List中 fdsfdsfsfs
@Data
@Configuration
@ConfigurationProperties(prefix = "aa.white")  //通过这个注解读取配置文件
public class InterceptorConfig {
    public List<String> urls;
}
