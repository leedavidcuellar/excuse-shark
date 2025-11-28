package com.excusasshark.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa una excusa tech generada
 * Composición de: contexto + causa + consecuencia + recomendación
 */
@Entity
@Table(name = "excuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Excuse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String contexto;

    @Column(nullable = false, length = 2000)
    private String causa;

    @Column(nullable = false, length = 2000)
    private String consecuencia;

    @Column(nullable = false, length = 2000)
    private String recomendacion;

    @Column(length = 500)
    private String meme;

    @Column(length = 1000)
    private String ley;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private RoleType roleTarget;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "boolean default true")
    @Builder.Default
    private Boolean active = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
