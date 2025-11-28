package com.excusasshark.service.mapper;

import com.excusasshark.dto.FragmentRequestDTO;
import com.excusasshark.dto.FragmentResponseDTO;
import com.excusasshark.model.Fragment;
import com.excusasshark.model.FragmentType;

/**
 * Mapper estático null-safe para Fragment
 */
public class FragmentMapper {

    private FragmentMapper() {
        // Evitar instanciación
    }

    public static Fragment toEntity(FragmentRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        FragmentType type = null;
        try {
            type = FragmentType.valueOf(dto.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            // Ignorar valor inválido
        }

        return Fragment.builder()
                .type(type)
                .text(dto.getText())
                .source(dto.getSource())
                .category(dto.getCategory())
                .build();
    }

    public static FragmentResponseDTO toResponse(Fragment fragment) {
        if (fragment == null) {
            return null;
        }

        return FragmentResponseDTO.builder()
                .id(fragment.getId())
                .type(fragment.getType())
                .text(fragment.getText())
                .source(fragment.getSource())
                .category(fragment.getCategory())
                .createdAt(fragment.getCreatedAt())
                .updatedAt(fragment.getUpdatedAt())
                .active(fragment.getActive())
                .build();
    }

    public static void updateEntity(FragmentRequestDTO dto, Fragment existing) {
        if (dto == null || existing == null) {
            return;
        }

        if (dto.getText() != null) {
            existing.setText(dto.getText());
        }
        if (dto.getSource() != null) {
            existing.setSource(dto.getSource());
        }
        if (dto.getCategory() != null) {
            existing.setCategory(dto.getCategory());
        }
    }
}
