package com.excusasshark.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear/actualizar fragmentos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FragmentRequestDTO {

    @NotBlank(message = "El tipo de fragmento es requerido")
    private String type;

    @NotBlank(message = "El texto del fragmento es requerido")
    @Size(min = 5, max = 2000, message = "El texto debe tener entre 5 y 2000 caracteres")
    private String text;

    @Size(max = 500, message = "La fuente no puede exceder 500 caracteres")
    private String source;

    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    private String category;
}
