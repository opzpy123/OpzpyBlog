package com.opzpy123.mypeojectdemo.dto;

import com.opzpy123.mypeojectdemo.enums.Status;
import com.opzpy123.mypeojectdemo.exception.BaseException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> of(Integer code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    public static <T> ApiResponse<T> ofSuccess() {
        return ofSuccess(null);
    }

    public static <T> ApiResponse<T> ofSuccess(T data) {
        return ofStatus(Status.SUCCESS, data);
    }

    public static <T> ApiResponse<T> ofStatus(Status status) {
        return ofStatus(status, null);
    }

    public static <T> ApiResponse<T> ofStatus(Status status, T data) {
        return of(status.getCode(), status.getMessage(), data);
    }

    public static <E extends BaseException> ApiResponse<String> ofException(E e) {
        return of(e.getCode(), e.getMessage(), e.getData());
    }
}
