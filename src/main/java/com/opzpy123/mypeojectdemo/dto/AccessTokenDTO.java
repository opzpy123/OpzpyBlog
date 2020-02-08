package com.opzpy123.mypeojectdemo.dto;

import lombok.Data;

/**
 * 封装githubProvider参数
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
