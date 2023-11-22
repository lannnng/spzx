package com.learn.exception;


import com.learn.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*前后端分离项目使用这个注解,已经包含@Component 这个注解
*
* 异常处理器的作用：当controller层出现异常的时候，异常处理器可以捕获这个异常，执行异常处理器内的方法，处理成和成功状态相同格式的json字符串
* @RestControllerAdvice 加上这个注解 包含 @Component的功能 和返回为json字符串的功能
*
* */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //表示处理异常的范围，这里是处理所有异常
    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e){
        //org.apache.commons.lang3.exception.ExceptionUtils 里面的ExceptionUtils 打印的信息比较多
        if (e!=null){
          log.error("出现异常：{}", ExceptionUtils.getStackTrace(e));

        }
        return  Result.fail();

    }

    @ExceptionHandler(value = CustomException.class)
    public Result exception(CustomException e){
        //org.apache.commons.lang3.exception.ExceptionUtils 里面的ExceptionUtils 打印的信息比较多
        if (e.getE()!=null){
            log.error("出现异常：{}", ExceptionUtils.getStackTrace(e.getE()));

        }

        return  Result.fail().code(e.getCode()).message(e.getMessage());

    }

}
