package com.network.management.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 主页控制器
 *
 * @author yusheng
 */
@RestController
public class IndexController {

    /**
     * 登录调整首页逻辑
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl = request.getParameter("redirectUrl");
        if(StringUtils.isNotBlank(redirectUrl)){
            if(redirectUrl.startsWith("http://") || redirectUrl.startsWith("https://")){
                response.sendRedirect(redirectUrl);
            }else {
                response.sendRedirect("http://" + redirectUrl);
            }
        }
        response.sendRedirect("https://github.com/qianniaoliu/network-management");
    }
}
