// package com.example.customer.configuration;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;

// import com.example.customer.handler.OAuth2SuccessHandler;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

// @Autowired
// private OAuth2SuccessHandler oAuth2SuccessHandler;

// @Bean
// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// http.cors().and().csrf().disable()
// .authorizeHttpRequests(authorize -> authorize
// .requestMatchers("/api/address/**").permitAll()
// // .requestMatchers("/api/customer/**").permitAll()
// .anyRequest().authenticated())
// .sessionManagement(
// (session) -> session
// .maximumSessions(10)
// .maxSessionsPreventsLogin(true))
// .formLogin(form -> form
// .loginPage("/login")
// .defaultSuccessUrl("http://localhost:8080", true)
// .permitAll())
// .oauth2Login(oauth2 -> oauth2
// .loginPage("/login")
// .successHandler(oAuth2SuccessHandler));
// return http.build();
// }
// }
