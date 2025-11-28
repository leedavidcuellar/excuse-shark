package com.excusasshark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de memes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemeResponseDTO {
    private Long id;
    private String text;
    private String author;
    private String source;
    private String category;
    private LocalDateTime createdAt;
    private Boolean active;
}
