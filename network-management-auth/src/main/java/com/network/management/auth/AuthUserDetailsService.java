package com.network.management.auth;

import com.network.management.User;
import com.network.management.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2020-09-07
 */
@Component
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser(s);
        sysUser.setId(1L);
        sysUser.setPassword(validUser(s));
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ADMIN");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(grantedAuthority);
        sysUser.setAuthorities(authorities);
        return sysUser;
    }

    public String validUser(String username){
        User user = userMapper.selectByUserName(username);
        if(user == null){
            return null;
        }else {
            return user.getPassword();
        }
    }
}
