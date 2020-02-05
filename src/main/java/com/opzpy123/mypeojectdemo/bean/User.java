package com.opzpy123.mypeojectdemo.bean;

import lombok.Data;

import java.io.Serializable;


@Data
public class User implements Serializable {
    private int id;
    private String name;
    private String password;

}
