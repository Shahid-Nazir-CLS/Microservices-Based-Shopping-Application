// package com.example.customer.configuration;

// import javax.sql.DataSource;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.jdbc.core.JdbcOperations;
// import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
// import
// org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
// import org.springframework.session.web.http.CookieSerializer;
// import org.springframework.session.web.http.DefaultCookieSerializer;
// import org.springframework.transaction.support.TransactionOperations;

// @Configuration
// @EnableJdbcHttpSession
// public class SessionConfig {

// // @Bean
// // public CookieSerializer cookieSerializer() {
// // DefaultCookieSerializer serializer = new DefaultCookieSerializer();
// // serializer.setCookieName("JSESSIONID");
// // serializer.setCookiePath("/");
// // serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
// // serializer.setUseBase64Encoding(false);
// // return serializer;
// // }
// }