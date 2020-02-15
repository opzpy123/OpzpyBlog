package com.opzpy123.mypeojectdemo.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SelectUserCookie {

    public static Cookie getUserCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cookie_user")) {
                    return cookie;

                }
            }
        }
        return null;
    }
}
