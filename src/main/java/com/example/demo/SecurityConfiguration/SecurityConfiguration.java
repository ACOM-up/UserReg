package com.example.demo.SecurityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER");//.authorities("ACCESS_INFO");
        //if you use authorities here it will get first in http other than the roles,
        //so make sure to doesn't mixed those up
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                //.anyRequest().authenticated()//should be authenticated for all requests
                //.anyRequest().permitAll()//allow all requests
                .antMatchers("/userInfo", "/userInfo/").hasAnyRole("USER", "ADMIN")//.hasAuthority("ACCESS_INFO")
                .antMatchers(HttpMethod.POST, "/add", "/add/").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/update/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/delete/{id}").hasRole("ADMIN")
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
