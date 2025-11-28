package com.excusasshark.service.mapper;

import com.excusasshark.dto.ExcuseResponseDTO;
import com.excusasshark.dto.MemeResponseDTO;
import com.excusasshark.dto.LawResponseDTO;
import com.excusasshark.model.Excuse;

/**
 * Mapper estático null-safe para Excuse
 */
public class ExcuseMapper {

    private ExcuseMapper() {
        // Evitar instanciación
    }

    public static ExcuseResponseDTO toResponse(Excuse excuse) {
        if (excuse == null) {
            return null;
        }

        return ExcuseResponseDTO.builder()
                .id(excuse.getId())
                .contexto(excuse.getContexto())
                .causa(excuse.getCausa())
                .consecuencia(excuse.getConsecuencia())
                .recomendacion(excuse.getRecomendacion())
                .roleTarget(excuse.getRoleTarget())
                .createdAt(excuse.getCreatedAt())
                .meme(excuse.getMeme() != null ? MemeResponseDTO.builder().text(excuse.getMeme()).build() : null)
                .ley(excuse.getLey() != null ? LawResponseDTO.builder().content(excuse.getLey()).build() : null)
                .build();
    }
}
