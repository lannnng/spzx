package com.learn.exception;

import com.learn.result.ResultCodeEnum;
import lombok.Data;
import lombok.Getter;

/*自定义异常类*/


@Getter
public class CustomException extends RuntimeException{

    public Integer code;
    public String message;
    public Exception e;

  public CustomException(Integer code,String message,Exception e){
//      super(message);
      this.code=code;
      this.message=message;
      this.e=e;
  }

    public CustomException(ResultCodeEnum resultCodeEnum,Exception e){
        this(resultCodeEnum.getCode(),resultCodeEnum.getMessage(),e);
    }

}
