package com.excusasshark.service.mapper;

import com.excusasshark.dto.LawResponseDTO;
import com.excusasshark.model.Law;

/**
 * Mapper estático null-safe para Law
 */
public class LawMapper {

    private LawMapper() {
        // Evitar instanciación
    }

    public static LawResponseDTO toResponse(Law law) {
        if (law == null) {
            return null;
        }

        return LawResponseDTO.builder()
                .id(law.getId())
                .type(law.getType() != null ? law.getType().name() : null)
                .content(law.getContent())
                .author(law.getAuthor())
                .createdAt(law.getCreatedAt())
                .active(law.getActive())
                .build();
    }
}
