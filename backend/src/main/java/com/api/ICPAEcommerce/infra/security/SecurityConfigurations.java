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

                .requestMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/users/list-users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/list-user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/update-user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/delete-user/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/products/register-product").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/products/list-products").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/products/list-products/name/{name}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/products/list-products/category/{category}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/products/list-product/code/{code}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/products/total-stock-value").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/products/update-product").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/delete-product/{code}").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/v1/orders/create-order").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/orders/list-order/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/orders/list-orders").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/orders/update-status/{id}").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/v1/authentication/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/authentication/forgot-password").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/authentication/change-password").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/v1/files/images").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/files/documents").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/files/downloads/{fileReferenceId}/{fileName}").permitAll()

                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
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
