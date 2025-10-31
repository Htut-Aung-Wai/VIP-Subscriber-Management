package com.mytel.vip_subscriber_management.caio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityUatConfig {

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
