package com.opzpy123.mypeojectdemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    SUCCESS(200, "成功"),
    LOGOUT(200, "成功登出"),
    BAD_REQUEST(400, "请求异常"),
    PARAM_NOT_MATCH(400, "参数不匹配"),
    PARAM_NOT_NULL(400, "参数不能为空"),
    UNAUTHORIZED(401, "没有访问权限"),
    ACCESS_DENIED(403, "权限不足"),
    USER_DISABLED(403, "当前用户已被禁用，请联系管理员解锁"),
    NOT_FOUND(404, "请求资源不存在"),
    HTTP_BAD_METHOD(405, "请求方式错误"),
    ERROR(500, "操作异常"),

    USER_EXISTS(6001,"用户已存在");

    private final int code;
    public final String message;

}
