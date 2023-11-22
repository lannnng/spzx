package com.learn.controller;

import com.learn.dto.system.LoginDto;
import com.learn.entity.system.SysUser;
import com.learn.result.Result;
import com.learn.service.SysUserService;
import com.learn.vo.system.Captcha;
import com.learn.vo.system.LoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/admin/system/index")
@Tag(name = "后台系统用户登录注销的接口")
public class SysUserController {
    @Resource
    SysUserService sysUserService;

    // 登录需要返回给前端的是token和refresh_token,这个两个属性创建一个logVo对象

    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto){
       /*springMvc自动获取到了dto对象，*/
       LoginVo loginVo=sysUserService.login(loginDto);
       return Result.success().data(loginVo);
    }
    // 生成验证码（captcha）这个方法，返回给前端的是--验证码的key,和包含验证码的图片

    @GetMapping("/getCaptcha")
    public Result generateCaptcha(){
         Captcha captcha=sysUserService.generateCaptcha();
         return Result.success().data(captcha);
    }
   // 根据请求头中的 token 获取获取用户信息 -------------------这个在前端中通过permission.js 中的前置路由守卫调用这个接口
    // 在拦截器中已经获取到了redis中，所以这个方法直接调用透传的登录状态，不用在分局token获取

    @GetMapping("/userInfo")
    public Result userInfo(){
        SysUser sysUser =sysUserService.getUserInfo();
        return  Result.success().data(sysUser);
    }
    // 注销---清除储存在redis中的token键值对

    @PutMapping("/logout")
   /* @Parameters({

    })*/
    public Result logout(@RequestHeader("token")String token){
           sysUserService.logout(token);
        return  Result.success().message("注销成功");
    }

}
