package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.user.User;
import com.api.ICPAEcommerce.domain.authentication.PasswordResetToken;
import com.api.ICPAEcommerce.repositories.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Serviço centralizado para gerenciar tokens de reset de senha
 * Responsável por criar, validar e consumir tokens persistidos em BD
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;

    @Value("${password.reset.token.expiration-minutes:15}")
    private int expirationMinutes;

    /**
     * Cria um novo token de reset de senha para o usuário
     * Token único (UUID) com validade configurável (padrão 15 min)
     */
    @Transactional
    public String createPasswordResetToken(User user) {
        String token = UUID.randomUUID().toString();
        Instant expiresAt = Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiresAt(expiresAt);
        resetToken.setUsed(false);

        tokenRepository.save(resetToken);
        log.info("Token de reset criado para usuário: {}", user.getEmail());

        return token;
    }

    /**
     * Valida e recupera o token
     * Retorna o token se válido (não expirado e não usado)
     */
    @Transactional(readOnly = true)
    public PasswordResetToken validateToken(String rawToken) {
        var optional = tokenRepository.findByToken(rawToken);

        if (optional.isEmpty()) {
            log.warn("Token de reset não encontrado: {}", rawToken);
            throw new IllegalArgumentException("Token inválido ou expirado");
        }

        PasswordResetToken token = optional.get();

        if (!token.isValid()) {
            log.warn("Token expirado ou já foi utilizado: {}", rawToken);
            throw new IllegalArgumentException("Token inválido ou expirado");
        }

        return token;
    }

    /**
     * Consome (marca como usado) o token após validação
     * Impede reutilização
     */
    @Transactional
    public void consumeToken(PasswordResetToken token) {
        token.markAsUsed();
        tokenRepository.save(token);
        log.info("Token de reset consumido para usuário: {}", token.getUser().getEmail());
    }
}

