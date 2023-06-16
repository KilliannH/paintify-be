package com.killiann.paintify.config;

import com.killiann.paintify.jwt.AuthEntryPointJwt;
import com.killiann.paintify.jwt.JwtFilter;
import com.killiann.paintify.jwt.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt authenticationEntryPoint;
    @Autowired
    private JwtFilter filter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests().requestMatchers("/api/signup", "/api/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        // fix H2 database console: Refused to display ' in a frame because it set 'X-Frame-Options' to 'deny'
        http.headers().frameOptions().sameOrigin();
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}