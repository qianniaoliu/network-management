package com.network.management.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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
@Api(tags = "主页控制器")
public class IndexController {


    @Value("${default.redirect.url}")
    private String defaultRedirectUrl;

    /**
     * 登录跳转首页逻辑
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/index")
    @ApiOperation("登录跳转首页逻辑")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl = request.getParameter("redirectUrl");
        if(StringUtils.isNotBlank(redirectUrl)){
            if(redirectUrl.startsWith("http://") || redirectUrl.startsWith("https://")){
                response.sendRedirect(redirectUrl);
            }else {
                response.sendRedirect("http://" + redirectUrl);
            }
        }
        response.sendRedirect(defaultRedirectUrl);
    }
}
