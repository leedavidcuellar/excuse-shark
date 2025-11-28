package com.excusasshark.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa una ley o axioma tech (Murphy, Hofstadter, Dilbert, DevOps, etc)
 */
@Entity
@Table(name = "laws")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Law {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LawType type;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(length = 200)
    private String author;

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
