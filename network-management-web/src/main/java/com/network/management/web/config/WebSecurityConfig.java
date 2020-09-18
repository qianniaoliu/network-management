package com.network.management.web.config;

import com.network.management.auth.AuthUserDetailsService;
import com.network.management.auth.JwtAuthenticationSuccessHandler;
import com.network.management.auth.RestAuthenticationEntryPoint;
import com.network.management.auth.UserAuthenticationProvider;
import com.network.management.web.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;

/**
 * @author yusheng
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthenticationProvider provider;
    private final AuthUserDetailsService authUserDetailsService;

    public WebSecurityConfig(UserAuthenticationProvider provider, AuthUserDetailsService authUserDetailsService) {
        this.provider = provider;
        this.authUserDetailsService = authUserDetailsService;
    }


    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler() {
        return new JwtAuthenticationSuccessHandler("/index");
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //开启跨域访问
        http.cors().disable();


        //设置用户验证跳转路径
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint())
                .and().authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                .successHandler(jwtAuthenticationSuccessHandler())
                .permitAll();



        //logout
        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .addLogoutHandler(new CookieClearingLogoutHandler("ACCESS_TOKEN", "JSESSIONID"))
                .permitAll();




        //新增JWT权限拦截器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailsService);
        auth.authenticationProvider(provider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/v2/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/webjars/**");
    }
}
