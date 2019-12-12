package com.wping.component.base.vo;

import com.wping.component.base.enums.ResultCodeEnum;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 5478191965825348899L;

    // 返回码(200000:成功 400001:失败 401001:认证失败(签名错误) 403001:权限不足 500001:服务器内部错误)
    private Integer code;

    // 返回消息，成功为“success”，失败为具体失败信息
    private String msg;

    // 返回数据
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result success() {
        return result(ResultCodeEnum.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        Result result = success();
        result.setData(data);
        return result;
    }

    public static Result fail() {
        return result(ResultCodeEnum.FAIL);
    }

    public static Result fail(String message) {
        Result result = new Result();
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMsg(message);
        return result;
    }

    public static Result systemBusy() {
        return result(ResultCodeEnum.SYSTEM_BUSY);
    }

    public static Result fail(int code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(message);
        return result;
    }

    public static Result result(ResultCodeEnum resultCodeEnum) {
        return new Result(resultCodeEnum.getCode(), resultCodeEnum.getDesc(), null);
    }

    public static boolean isSuccess(Integer code) {
        return ResultCodeEnum.SUCCESS.getCode().equals(code);
    }

    public static boolean isFail(Integer code) {
        return !isSuccess(code);
    }

}
