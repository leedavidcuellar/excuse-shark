package com.excusasshark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO modo ULTRA SHARK: Excusa + Meme + Ley
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UltraSharkExcuseDTO {
    private ExcuseResponseDTO excusa;
    private String meme;
    private String ley;
}
