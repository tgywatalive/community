package com.newcoder.community.controller.advice;

import com.newcoder.community.util.CommunityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {

    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error("服务器发生异常：" + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            log.error(element.toString());
        }

        // 判断是否为异步请求
        String xRequestedwith = request.getHeader("x-requested-with");
        if ("XMLHttpRequest".equals(xRequestedwith)) {
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(1, "服务器异常！"));
        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
