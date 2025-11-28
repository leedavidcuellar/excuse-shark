package com.excusasshark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de leyes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LawResponseDTO {
    private Long id;
    private String type;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private Boolean active;
}
