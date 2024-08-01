package br.com.desafio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf(AbstractHttpConfigurer::disable)
	            .authorizeHttpRequests(auth -> auth.requestMatchers(
	            	 "/v3/api-docs/**", 
	                 "/swagger-ui/**", 
	                 "/swagger-ui.html")
	                .permitAll()
	                .anyRequest()
	                .authenticated())
	            .httpBasic(Customizer.withDefaults());
	    return http.build(); 
	 }

}
