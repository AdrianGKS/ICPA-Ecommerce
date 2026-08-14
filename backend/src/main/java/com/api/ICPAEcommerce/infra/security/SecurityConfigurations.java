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

                        // USUÁRIOS (Público e Admin)
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/list-users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/list-user/{id}").hasRole("ADMIN")
                        // Idealmente, um usuário comum deveria poder atualizar/deletar o PRÓPRIO cadastro.
                        // Abordaremos isso mais abaixo.
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/update-user/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/delete-user/{id}").hasRole("ADMIN")

                        // PRODUTOS
                        // Apenas leitura é pública. Escrita requer ADMIN.
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/list-products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/list-product/code/{code}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/total-stock-value").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/register-product").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/update-product").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/delete-product/{code}").hasRole("ADMIN")

                        // PEDIDOS
                        // Criar pedido exige estar logado. Ver todos os pedidos e atualizar status exige ADMIN.
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders/create-order").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/list-order/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/list-orders").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/orders/update-status/{id}").hasRole("ADMIN")

                        // AUTENTICAÇÃO E RECUPERAÇÃO DE SENHA (Mantém-se público)
                        .requestMatchers(HttpMethod.POST, "/api/v1/authentication/**").permitAll()

                        // ARQUIVOS
                        .requestMatchers(HttpMethod.GET, "/api/v1/files/downloads/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/files/images").hasRole("ADMIN")

                        // SWAGGER
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
