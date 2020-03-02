package com.opzpy123.mypeojectdemo.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOTFOUND("问题不存在!");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
