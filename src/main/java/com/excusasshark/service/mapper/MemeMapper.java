package com.excusasshark.service.mapper;

import com.excusasshark.dto.MemeResponseDTO;
import com.excusasshark.model.Meme;

/**
 * Mapper estático null-safe para Meme
 */
public class MemeMapper {

    private MemeMapper() {
        // Evitar instanciación
    }

    public static MemeResponseDTO toResponse(Meme meme) {
        if (meme == null) {
            return null;
        }

        return MemeResponseDTO.builder()
                .id(meme.getId())
                .text(meme.getText())
                .author(meme.getAuthor())
                .source(meme.getSource())
                .category(meme.getCategory())
                .createdAt(meme.getCreatedAt())
                .active(meme.getActive())
                .build();
    }
}
