package com.vnmcodes.oauth_oicd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Authorize routes
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**", "/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                // OAuth2 login (Authorization Code)
                .oauth2Login(oauth -> oauth
                        .loginPage("/login") // custom login page
                )
                // Logout to home
                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll()
                )
                // For this demo, rely on default CSRF/session settings.
                .csrf(Customizer.withDefaults());

        return http.build();
    }
}

