package com.learn.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/*接收登录请求 得到的 userName 和 password*/
@Data
public class LoginDto {

    @Schema(title = "账号" )
    private String userName;
    @Schema(title = "密码")
    private String password;
    @Schema(title = "用户输入验证码")
    private String captchaCode;
    @Schema(title = "验证码key")
    private String captchaCodeKey;

}

