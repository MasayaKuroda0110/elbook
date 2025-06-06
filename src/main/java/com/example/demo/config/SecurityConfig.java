package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/login", "/register").permitAll()
	                    .requestMatchers("/admin/**").hasRole("Admin") // 管理者専用URL
	                    .requestMatchers("/user/**").hasRole("User")  // 一般ユーザー専用URL
						.anyRequest().authenticated())
				.formLogin(formLogin -> formLogin
						.loginPage("/login").permitAll())
				.logout(logout -> logout.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID"));

		return http.build();
	}

}
