package com.opzpy123.mypeojectdemo.provider;


import com.alibaba.fastjson.JSON;
import com.opzpy123.mypeojectdemo.dto.AccessTokenDTO;
import com.opzpy123.mypeojectdemo.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * 提供
 */
@Component
public class GitHubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create( JSON.toJSONString(accessTokenDTO),mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = Objects.requireNonNull(response.body()).string();
            return string.split("&")[0].split("=")[1];
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public GitHubUser getUser(String accessToken)  {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = Objects.requireNonNull(response.body()).string();
            return JSON.parseObject(string, GitHubUser.class);
        } catch (IOException e) {
        }
        return  null;

    }


}


