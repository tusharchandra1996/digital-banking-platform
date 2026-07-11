package com.digitalbanking.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

		    @Bean
		    SecurityFilterChain securityFilterChain(HttpSecurity http)
		            throws Exception {
	
		        http
		            .csrf(csrf -> csrf.disable())
		            .authorizeHttpRequests(auth -> auth
		                    .anyRequest().permitAll()
		            );
	
		        return http.build();
		    }

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//		http.csrf(csrf -> csrf.disable())
//		.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
//				.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
//				.requestMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll()
//				.requestMatchers(HttpMethod.POST, "/api/auth/forgot-password").permitAll()
//				.requestMatchers(HttpMethod.POST, "/api/auth/verify-otp").permitAll()
//				.requestMatchers(HttpMethod.POST, "/api/auth/reset-password").permitAll().anyRequest()
//				.authenticated())
//		.httpBasic(Customizer.withDefaults());
//
//		return http.build();
//	}
}
