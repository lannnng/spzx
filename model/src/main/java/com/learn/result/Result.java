package com.learn.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.swing.plaf.PanelUI;


/*T 代表任意类型的 数据   */
@Data
public class Result<T> {
    @Schema(title = "状态码",example = "200")
    private Integer code;
    @Schema(title = "状态信息",example = "成功")
    private String message;
    @Schema(title = "响应数据")
    private T data;

    /*构造器私有本类使用，只能用下面的静态方法*/
    private Result() {
    }

//方式1： 这种方式太固定，只能调用固定的方法，提示固定的信息

  /*成功或者失败都能调用这个方法，成功和失败的公共方法，被下面的 success和fail 调用
   private static <T> Result<T> build(T data,ResultCodeEnum resultCodeEnum){
       Result<T> result = new Result<>();
       result.setData(data);
       result.setCode(resultCodeEnum.getCode());
       result.setMessage(resultCodeEnum.getMessage());
       return  result;
   }

   // 成功传入数据data，设置成功的信息，直接调用方法直接传入data
   public static <T> Result<T> success(){
       return build(null,ResultCodeEnum.SUCCESS);
   }
   // 失败，不传入数据，设置失败的信息，直接调用这个方法啥也不用传
   public static <T> Result<T> fail(){
       return build(null,ResultCodeEnum.FAIL);
   }*/


    public static <T> Result<T> setResult(T data, Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    //
    public static <T> Result<T> setResult(T data, ResultCodeEnum resultCodeEnum) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    //  设定基础的成功和失败的方法，后面有更多的错误情况出现，在基础的成功和失败的方法上修改成功与失败的code和message
    public static <T> Result<T> success() {
        return setResult(null, ResultCodeEnum.SUCCESS);
    }

    public static <T> Result<T> fail() {
        return setResult(null, ResultCodeEnum.FAIL);
    }

    /*先使用上面的基础方法，在使用这个链式调用设置自定义的错误信息*/
    public Result<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result<T> data(T data) {
        this.setData(data);
        return this;
    }


}
