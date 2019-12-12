package com.wping.component.base.enums;

/**
 * 响应码枚举：3位http status code + 3 位 bizCode，例如: 200（http成功） + 000(业务成功)， 401(http 未授权) + 001（签名错误）
 */
public enum ResultCodeEnum {
    SUCCESS(200000, "成功"),
    REDIRECT(302001, "重定向"),
    FAIL(400001, "失败"),
    UNAUTHORIZED(401001, "认证失败(签名错误)"),
    UNAUTHORIZED_TOKEN(401002, "token验证失败或失效"),
    FORBIDDEN(403001, "权限不足"),
    INTERNAL_SERVER_ERROR(500001, "服务器内部错误"),
    SYSTEM_BUSY(500002, "系统繁忙,请稍候再试"),
    ;

    private Integer code;
    private String desc;

    ResultCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
