package com.network.management.auth;

import com.network.management.domain.dao.User;
import com.network.management.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yusheng
 */
@Component
public class AuthUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public AuthUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser(s);
        sysUser.setId(110L);
        sysUser.setPassword(validUser(s));
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ADMIN");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(grantedAuthority);
        sysUser.setAuthorities(authorities);
        return sysUser;
    }

    public String validUser(String username){
        User user = userService.queryByName(username);
        if(user == null){
            return null;
        }else {
            return user.getPassword();
        }
    }
}
