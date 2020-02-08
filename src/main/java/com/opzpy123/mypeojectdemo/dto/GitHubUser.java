package com.opzpy123.mypeojectdemo.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class GitHubUser {
    private String name;
    private Long id;
    //用户描述
    private String bio;
}
