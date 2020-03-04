package com.opzpy123.mypeojectdemo.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOTFOUND(2001,"问题不存在!"),
    TARGET_PARAM_NOTFOUND(2001,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"未登录，当前操作需要登录,请在登陆后重试"),
    SYSTEM_ERROR(2004,"请求超时，请稍后再试"),
    TYPE_PARAM_WRONG(2005,"评论类型错误，或不存在"),
    COMMENT_NOTFOUND(2006,"你要回复的评论不存在"),
    CONTENT_NULL(2007,"回复不能为空"),
    ;

    private Integer code;
    private String message;
    

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

}
