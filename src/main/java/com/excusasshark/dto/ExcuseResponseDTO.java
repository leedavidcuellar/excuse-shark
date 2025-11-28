package com.excusasshark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.excusasshark.model.RoleType;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para excusas simples
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcuseResponseDTO {
    private Long id;
    private String contexto;
    private String causa;
    private String consecuencia;
    private String recomendacion;
    private RoleType roleTarget;
    private LocalDateTime createdAt;
    private MemeResponseDTO meme;
    private LawResponseDTO ley;
}
