package com.example.demo.security;

import com.example.demo.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/static/**",
                "/css/**", "/js/**", "/images/**",
                "/register", "/api/v1/users/**", "/reg", "/login").permitAll()
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/v1/urls/**").permitAll()
            .requestMatchers("/{shortUrl}").permitAll()
            .requestMatchers("/", "/{shortUrl}").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .formLogin(formLogin -> formLogin
            .loginPage("/login")
            .loginProcessingUrl("/perform_login")
            .defaultSuccessUrl("/", true)
            .failureUrl("/login?error=true")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/perform_logout")
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/login?logout=true")
            .permitAll()
        )
        .exceptionHandling(exceptions -> exceptions
            .accessDeniedPage("/access-denied")
        );
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public LogoutSuccessHandler logoutSuccessHandler() {
    return (request, response, authentication) -> response.sendRedirect("/login?logout");
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class).build();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return (request, response, accessDeniedException) -> {
      response.sendRedirect("/access-denied");
    };
  }
}
