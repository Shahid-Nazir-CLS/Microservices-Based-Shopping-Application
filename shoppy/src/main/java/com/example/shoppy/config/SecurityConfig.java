package com.example.shoppy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import
org.springframework.security.config.annotation.web.builders.HttpSecurity;
import
org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                .requestMatchers("/", "http://localhost:8080", "/resources/**", "/itemDetails/**", "/search").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(session -> session
                .maximumSessions(10)
                .maxSessionsPreventsLogin(true))
            .formLogin(form -> form
                .loginPage("http://localhost:8082/login2")
                .defaultSuccessUrl("/", true)
                .permitAll());
            // .oauth2Login(oauth2 -> oauth2
            //     .loginPage("/login2")
            //     .successHandler(oAuth2SuccessHandler))
            //  .logout(logout -> logout
            //     .logoutUrl("/logout")
            //     .logoutSuccessUrl("/")
            //     .invalidateHttpSession(true)
            //     .deleteCookies("JSESSIONID")
            //     .permitAll());
            
        return http.build();
    }
}
