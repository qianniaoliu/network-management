package com.network.management.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author yusheng
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final AuthUserDetailsService authUserDetailsService;

    public UserAuthenticationProvider(AuthUserDetailsService authUserDetailsService) {
        this.authUserDetailsService = authUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        SysUser user = (SysUser) authUserDetailsService.loadUserByUsername(username);
        if(user.getPassword() == null || !user.getPassword().equals(password)){
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(username,password,authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
