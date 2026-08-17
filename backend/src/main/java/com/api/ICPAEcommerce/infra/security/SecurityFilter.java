package com.api.ICPAEcommerce.infra.security;

import com.api.ICPAEcommerce.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityToken securityToken;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recoverToken(request);

        if (tokenJWT != null) {
            try {
                // Tenta validar o token
                var subject = securityToken.getSubject(tokenJWT);
                var user = userRepository.findByEmail(subject);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (RuntimeException ex) {
                // Se o token for inválido, tem aspas ou expirou, a exceção é capturada aqui.
                // Não fazemos nada. O SecurityContextHolder continuará vazio (usuário anônimo).
                // O Spring Security decidirá se a rota requer login ou se é permitAll().
                System.out.println("Aviso: Token recebido é inválido. Motivo: " + ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            // Remove o "Bearer " e também garante que não há aspas ou espaços extras
            return authorizationHeader.replace("Bearer ", "").replace("\"", "").trim();
        }

        return null;
    }
}