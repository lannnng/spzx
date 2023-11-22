package com.learn.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.learn.dto.system.LoginDto;
import com.learn.entity.system.SysUser;
import com.learn.exception.CustomException;
import com.learn.interceptor.MyLoginInterceptor;
import com.learn.mapper.SysUserMapper;
import com.learn.result.ResultCodeEnum;
import com.learn.service.SysUserService;
import com.learn.vo.system.Captcha;
import com.learn.vo.system.LoginVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    MyLoginInterceptor myLoginInterceptor;
    @Override
    public LoginVo login(LoginDto loginDto) {
        //loginDto不可能为空对象，
        String userName = loginDto.getUserName();
        String password = loginDto.getPassword();
        String captchaCode = loginDto.getCaptchaCode();
        String captchaCodeKey = loginDto.getCaptchaCodeKey();
/**
 * 防止高端玩家，通过不正常的手段登录
 *
  */
        // 防止用户名为 null 和 "" 的现象出现
        if (StringUtils.isEmpty(userName)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(captchaCode)
                || StringUtils.isEmpty(captchaCodeKey)
        ){
           // throw new RuntimeException("用户名或密码异常");
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR_NOTINPUT,null);
        }
/**
 * 查询redis中存储的内容------验证 验证码 是否正确
  */
        String cacheCode = stringRedisTemplate.opsForValue().get("user:login:captcha:" + captchaCodeKey); //获取redis中存储的key和value
        if (!captchaCode.equalsIgnoreCase(cacheCode)){
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR_CAPTCHAMISTAKE,null);
        }
        stringRedisTemplate.delete("user:login:captcha:" + captchaCodeKey); //清除redis中键值对
/**
 * 查询数据库中的信息-----验证用户名和密码
 */
        /*1.通过DTO对象的userName,查找出sysUser对象*/
        SysUser sysUser = sysUserMapper.findSysUserByUserName(userName);

        /*如果为空，后端抛异常，但是如何把这个消息返回给前端？？？ -------- 通过异常处理器，捕获异常对象，获得下面的异常对象中的code和message*/
        if (sysUser == null){
//            throw new RuntimeException("用户名不存在");
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR_ACCOUNTNOTFOUND,null);
        }
        /*2.查到了sysUser这个对象，判断密码（md5加密）是否正确*/
        String inputPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!inputPassword.equals(sysUser.getPassword())){
//            throw new RuntimeException("密码错误");
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR_PASSWORDMISTAKE,null);
        }
        /*3.查询状态 0为停用,1 不停用*/
        if (sysUser.getStatus()!=1){
//            throw new RuntimeException("账户停用");
             throw new CustomException(ResultCodeEnum.LOGIN_ERROR_ACCOUNTSTOP,null);
        }

/**
 * 返回前端的内容，生成token和保存token到redis中，设置过期时间
  */
        /*4.通过hutool包的雪花算法生成token*/
        String token = IdUtil.getSnowflake(1,1).nextIdStr();
        LoginVo loginVo=new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("1111");
        /*把token放在redis中，并设置过期时间*/
        stringRedisTemplate.opsForValue().set("user:login:"+token,
                JSON.toJSONString(sysUser),5, TimeUnit.DAYS);
        return loginVo;


    }

    /*生成图形验证码的方法, 给前端的是 base64格式图片，和在redis中存储的键值对(idStr+验证码)中的idStr*/
    @Override
    public Captcha generateCaptcha() {
        //1.通过hutool工具包生成验证码图片
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(80, 40, 4, 20);
        //拿到验证码图片中验证码
        String code = circleCaptcha.getCode();
        //把验证码图转换为img64格式，传输给前端
        String imageBase64 = circleCaptcha.getImageBase64();

        //2.把验证码存入redis中，生成唯一的key
        String idStr = IdUtil.getSnowflake(1, 1).nextIdStr();
        stringRedisTemplate.opsForValue().set("user:login:captcha:"+idStr,code,1,TimeUnit.MINUTES);

        Captcha captcha =new Captcha();
        captcha.setCaptchaCodeKey(idStr);
        captcha.setCaptchaImg(imageBase64);
        return captcha;
    }

    @Override
    public SysUser getUserInfo() {
        //通过这样拦截器里面ThreadLocal获取已经从拦截器中的获取到的对象
        SysUser sysUser = MyLoginInterceptor.THREAD_LOCAL.get();
        sysUser.setPassword(null);
        return sysUser;
    }

    @Override
    public void logout(String token) {
        stringRedisTemplate.delete("user:login:"+token);
    }


}
