package com.api.ICPAEcommerce.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                .requestMatchers(HttpMethod.POST, "/api/v1/users/create-user").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/users/list-users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/list-user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/update-user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/users/recover-password").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users/update-password/{token}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/delete-user/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/products/create-product").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/products/list-products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/products/update-product/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/delete-product/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/products/{id}/create-image").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/products/{produtoId}/list-images").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/products/update-image/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/delete-image/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/login/authentication").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/login/register").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/v1/orders/create-order").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/orders/list-order/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/orders/list-orders").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/orders/update-status/{id}").permitAll()

                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                .anyRequest().authenticated()
                .and()).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
