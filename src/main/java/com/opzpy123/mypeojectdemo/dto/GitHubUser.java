package com.opzpy123.mypeojectdemo.dto;

import lombok.Data;
import org.springframework.stereotype.Component;


public class GitHubUser {
    private String name;
    private String username;
    private Long id;
    //用户描述
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 数据库命名的username,这里获取的是name,共用一段的前台session判空user.username数据库难改，这里好改。。
     * 别问，问就是方便
     * @return
     */
    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "GitHubUser{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }
}
