package com.itmk.config.security;

import com.itmk.config.security.customerdetailservice.CustometUserDetailService;
import com.itmk.config.security.filter.CheckTokenFilter;
import com.itmk.config.security.handler.CustomAccessDeineHandler;
import com.itmk.config.security.handler.LoginFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring security配置类
 */
@Configuration //表明该类是一个配置类
@EnableWebSecurity //启用spring security
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用spring security注解
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustometUserDetailService custometUserDetailService;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private CustomAccessDeineHandler customAccessDeineHandler;
    @Autowired
    private CheckTokenFilter checkTokenFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //解决跨域问题
        http.cors().and().headers().frameOptions().disable();
        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //除了/api/user/login请求之外的所有请求都需要权限验证
        // http.csrf().disable() 关闭跨域请求伪造
        http.csrf().disable()
                //基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/api/user/login").permitAll()
                .anyRequest().authenticated()
        .and().exceptionHandling()
        .authenticationEntryPoint(loginFailureHandler)
        .accessDeniedHandler(customAccessDeineHandler);
    }

    //配置我们自定义的UserDetailService
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(custometUserDetailService);
    }

    //注入AuthenticationManager
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
