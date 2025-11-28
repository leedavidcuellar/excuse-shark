package com.excusasshark.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa un fragmento de excusa (Contexto, Causa, Consecuencia, Recomendación)
 */
@Entity
@Table(name = "fragments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fragment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FragmentType type;

    @Column(nullable = false, length = 2000)
    private String text;

    @Column(length = 500)
    private String source;

    @Column(length = 50)
    private String category;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "boolean default true")
    @Builder.Default
    private Boolean active = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
