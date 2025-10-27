package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@Order(1)
public class SecurityConfigConfirmPage extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilterConfirmPage jwtAuthenticationFilterConfirmPage;

    public SecurityConfigConfirmPage(JwtAuthenticationFilterConfirmPage jwtAuthenticationFilterConfirmPage) {
        this.jwtAuthenticationFilterConfirmPage = jwtAuthenticationFilterConfirmPage;
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/confirm-page/auth/**").permitAll()
                .antMatchers(
                        "/confirm-page/expire-subscribers/**"
                ).authenticated()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilterConfirmPage, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/confirm-page/**") // only for confirm-page routes
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/confirm-page/auth/**").permitAll()
                .antMatchers("/confirm-page/expire-subscribers/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilterConfirmPage, UsernamePasswordAuthenticationFilter.class);
    }


/*@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));  // Use this instead of setAllowedOrigins("*")
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);  // Keep this true if you're using cookies or authentication headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/


}



