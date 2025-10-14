package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfigUat {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // allow POST, PUT, DELETE without token
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();      // use basic auth (admin / 12345)
        return http.build();
    }
}
