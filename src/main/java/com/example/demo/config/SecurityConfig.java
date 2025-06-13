package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SpringSecurityの設定クラス
 */
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
						.requestMatchers("/management").hasRole("ADMIN")
						.requestMatchers("/userManagement").hasRole("ADMIN")
						.requestMatchers("/regBook").hasRole("ADMIN")
						.anyRequest().authenticated())
				.formLogin(formLogin -> formLogin
						.loginPage("/login").permitAll()
						.defaultSuccessUrl("/home", true))
				.logout(logout -> logout
						.logoutUrl("/sidebar/logout")
						.logoutSuccessUrl("/login?logout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID"));
		return http.build();
	}

}
