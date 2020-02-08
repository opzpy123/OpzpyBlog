package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.dto.AccessTokenDTO;
import com.opzpy123.mypeojectdemo.dto.GitHubUser;
import com.opzpy123.mypeojectdemo.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class OauthController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientid;
    @Value("${github.client.secret}")
    private String secret;
    @Value("${github.redirecturi}")
    private String redirecturi;
    /**
     * 获取路径变量
     *
     * @param code
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirecturi);
        accessTokenDTO.setState("1");
        accessTokenDTO.setClient_id(clientid);
        accessTokenDTO.setClient_secret(secret);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user);
       if(user!=null){
           //登录成功写入cookie和session;
           HttpSession session = request.getSession();
           session.setAttribute("user",user);
           return "redirect:/";

       }else{
           //登录失败请重新登陆
           return "redirect:/";
       }
    }


}
