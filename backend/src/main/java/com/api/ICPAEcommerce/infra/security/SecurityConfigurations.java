package com.api.ICPAEcommerce.infra.security;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()

                .requestMatchers(HttpMethod.POST, "/api/v1/products/create-product").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/products/list-products/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                .requestMatchers(HttpMethod.PUT, "/api/v1/products/update-product/{id}").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")

                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/delete-product/{id}").hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/products/{id}/create-image").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/products/{produtoId}/list-images").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                .requestMatchers(HttpMethod.PUT, "/api/v1/products/update-image/{id}").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")

                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/delete-image/{id}").hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/users/authentication").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users/create-user").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/users/list-user/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/update-user/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users/recover-password").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users/update-password/{token}").permitAll()

                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/v1/users/list-users").hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/delete-user/{id}").hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/orders/create-order").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/orders/list-order/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/orders/list-orders").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/orders/update-status/{id}").permitAll()

                .anyRequest().authenticated()
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
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
