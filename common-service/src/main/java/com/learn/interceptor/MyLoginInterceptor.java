package com.learn.interceptor;

import com.alibaba.fastjson.JSON;
import com.learn.entity.system.SysUser;
import com.learn.exception.CustomException;
import com.learn.result.ResultCodeEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class MyLoginInterceptor implements HandlerInterceptor {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    //创建透传对象
    public static final ThreadLocal<SysUser> THREAD_LOCAL=new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();

        //预检请求的 请求方式为 OPTIONS
        //当请求方式为OPTIONS的时候就放行，就是所有的 预检请求 都放行
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(method, "OPTIONS")) {
            return true;
        }

        //根据token 获取 sysUser对象字符串，这里已经获取到了，后面就不用再获取一次-------------- 透传登录状态（传递已经从redis中获取到的对象）
        String token = request.getHeader("token");
        String sysUserJson = null;
        if (StringUtils.hasLength(token)) {
            sysUserJson = stringRedisTemplate.opsForValue().get("user:login:" + token);
        }

        if (StringUtils.isEmpty(sysUserJson)) {
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR_STATUEMISTAKE, null);
        }

        // 创建ThreadLocal的对象透传redis中的存储的对象，放入THREAD_LOCAL中
        SysUser sysUser = JSON.parseObject(sysUserJson, SysUser.class);
        THREAD_LOCAL.set(sysUser);


        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //拦截完成之后清除对象
        THREAD_LOCAL.remove();
    }
}
