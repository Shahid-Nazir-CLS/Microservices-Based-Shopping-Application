package com.example.customer.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import
org.springframework.security.config.annotation.web.builders.HttpSecurity;
import
org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.example.customer.handler.OAuth2SuccessHandler;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

@Autowired
private OAuth2SuccessHandler oAuth2SuccessHandler;

@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                .requestMatchers("/login2", "api/v1/customers/session", "http://localhost:8080", "/resources/**", "/itemDetails/**", "/search").permitAll()
                .anyRequest().permitAll())
            .sessionManagement(session -> session
                .maximumSessions(10)
                .maxSessionsPreventsLogin(true))
            .formLogin(form -> form
                .loginPage("/login2")
                .defaultSuccessUrl("http://localhost:8080", true)
                .permitAll())
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login2")
                .successHandler(oAuth2SuccessHandler))
             .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("http://localhost:8080")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll());
            
        return http.build();
    }
}
