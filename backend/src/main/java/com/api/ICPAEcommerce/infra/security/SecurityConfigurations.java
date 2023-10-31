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

                .requestMatchers(HttpMethod.POST, "/api/v2/users/register").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v2/users/list-users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v2/users/list-user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v2/users/update-user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v2/users/delete-user/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v2/products/create-product").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v2/products/list-products/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v2/products/update-product").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/v2/products/delete-product/{id}").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/v2/orders/create-order").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v2/orders/list-order/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v2/orders/list-orders").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v2/orders/update-status/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v2/authentication/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v2/authentication/forgot-password").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v2/authentication/change-password").permitAll()

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
