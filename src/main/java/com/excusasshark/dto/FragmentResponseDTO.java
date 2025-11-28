package com.excusasshark.dto;

import com.excusasshark.model.FragmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para fragmentos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FragmentResponseDTO {
    private Long id;
    private FragmentType type;
    private String text;
    private String source;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;
}
