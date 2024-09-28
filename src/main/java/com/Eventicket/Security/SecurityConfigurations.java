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
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/registrar").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/user/alterar-usuario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/event/criar-evento").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/event/selecao-de-jurados").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/idea/distribuir-ideias").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/user/listar-usuarios").hasRole("COLABORADOR")
                        .requestMatchers(HttpMethod.POST, "/api/idea/postar-ideia").hasRole("COLABORADOR")
                        .requestMatchers(HttpMethod.GET, "/api/idea/listar-ideias").hasRole("COLABORADOR")
                        .requestMatchers(HttpMethod.GET, "/api/idea/top-ideias-votadas").hasRole("COLABORADOR")
                        .requestMatchers(HttpMethod.POST, "/api/idea/avaliar").hasRole("AVALIADOR")
                        .requestMatchers(HttpMethod.POST, "/api/event/listar-eventos").hasRole("COLABORADOR")
                        .requestMatchers(HttpMethod.POST, "/api/idea/voto-popular").hasRole("COLABORADOR")
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
