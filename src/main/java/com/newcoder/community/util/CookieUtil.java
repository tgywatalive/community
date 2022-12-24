package com.newcoder.community.util;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/*
* 获取指定Cookie值工具类
* */
public class CookieUtil {
    public static String getValue(HttpServletRequest request, String name) {
        if (request == null || name == null) {
            throw new IllegalArgumentException("参数为空！");
        }
        // 获取所有的Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
