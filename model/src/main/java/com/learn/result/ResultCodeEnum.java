package com.learn.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(-1,"服务器内部错误"),

 // 自定义业务上的错误
    LOGIN_ERROR_NOTINPUT(1000,"没有输入登录信息"),
    LOGIN_ERROR_CAPTCHAMISTAKE(1001,"验证码错误"),
    LOGIN_ERROR_ACCOUNTNOTFOUND(1002,"账户不存在"),
    LOGIN_ERROR_PASSWORDMISTAKE(1003,"密码错误"),
    LOGIN_ERROR_ACCOUNTSTOP(1004,"账户停用"),
    LOGIN_ERROR_STATUEMISTAKE(1005,"登录状态错误"), //没有找到token
    INPUT_ERROR_PARAMAMISTAKE(1006,"参数异常"),








    ;

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
