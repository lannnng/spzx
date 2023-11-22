package com.learn.service;

import com.learn.dto.system.LoginDto;
import com.learn.entity.system.SysUser;
import com.learn.vo.system.Captcha;
import com.learn.vo.system.LoginVo;

public interface SysUserService {

    LoginVo login(LoginDto loginDto);


    Captcha generateCaptcha();


    SysUser getUserInfo();

    void logout(String token);
}
