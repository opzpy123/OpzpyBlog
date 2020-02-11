package com.opzpy123.mypeojectdemo.bean;

import lombok.Data;

import java.io.Serializable;


/**
 * lombok自动生成get,set
 */
@Data
public class User implements Serializable {
    private Long id;
    private String username;
    //对于github用户，密码是用户id
    private String password;

}
