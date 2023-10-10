package com.api.ICPAEcommerce.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity (name = "Token")
@Table(name = "tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private OffsetDateTime created;
    private OffsetDateTime expires;
    private OffsetDateTime confirmed;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
