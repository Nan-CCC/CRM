package com.example.enterprisecrm.common.result;

public enum ResultCode {
    // 自定义枚举内容
    SUCCESS(200, "Success"),
    WARNING(401,"token过期"),
    ERROR(400, "Error");


    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
