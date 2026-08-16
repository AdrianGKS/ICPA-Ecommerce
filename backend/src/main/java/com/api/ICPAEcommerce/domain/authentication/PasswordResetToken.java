package com.api.ICPAEcommerce.domain.authentication;

import com.api.ICPAEcommerce.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * Entidade para tokens de reset de senha persistidos
 * Cada token é válido por um período limitado e pode ser usado apenas uma vez
 */
@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private Boolean used = false;

    /**
     * Verifica se o token ainda é válido (não expirado e não foi usado)
     */
    public boolean isValid() {
        return !used && Instant.now().isBefore(expiresAt);
    }

    /**
     * Marca o token como usado
     */
    public void markAsUsed() {
        this.used = true;
    }
}

