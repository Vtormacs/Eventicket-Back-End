package com.Eventicket.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/registrar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/validar-conta").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user/update/{id}").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/user/delete/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user/findAll").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user/findById/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user/buscarEventos/{idUsuario}").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/buy/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/buy/sell/{idUsuario}").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/buy/delete/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/buy/findAll").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/buy/findById/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
