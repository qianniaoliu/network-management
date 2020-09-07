package com.network.management.auth;

import com.network.management.auth.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author yusheng
 * @date 2020-09-07
 */
@Component
public class JwtAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final static Logger logger = LoggerFactory.getLogger(JwtAuthenticationSuccessHandler.class);

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.expire}")
    private Integer expire;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationSuccessHandler(){}

    public JwtAuthenticationSuccessHandler(String defaultTargetUrl){
        this.setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录校验成功!");
        String username = authentication.getName();
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        String token = jwtTokenUtil.generatorToken(sysUser,expire);
        response.setHeader(header,token);
        Cookie cookie = new Cookie(header,token);
        response.addCookie(cookie);
        this.handler(request,response,authentication);
    }

    public void handler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        DefaultSavedRequest defaultSavedRequest = null;
        if(savedRequest instanceof DefaultSavedRequest){
            defaultSavedRequest = (DefaultSavedRequest) savedRequest;
        }
        if (savedRequest == null || (defaultSavedRequest != null && "/".equals(defaultSavedRequest.getRequestURI()))) {
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            String targetUrlParameter = this.getTargetUrlParameter();
            if (!this.isAlwaysUseDefaultTargetUrl() && (targetUrlParameter == null || !StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
                this.clearAuthenticationAttributes(request);
                String targetUrl = savedRequest.getRedirectUrl();
                this.logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
                this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
            } else {
                this.requestCache.removeRequest(request, response);
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }
}
