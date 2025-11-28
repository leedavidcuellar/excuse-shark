package com.excusasshark.service.mapper;

import com.excusasshark.dto.ExcuseResponseDTO;
import com.excusasshark.model.Excuse;
import com.excusasshark.model.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ExcuseMapper Tests")
class ExcuseMapperTest {

    @Test
    @DisplayName("toResponse con excuse válida debe retornar DTO completo")
    void testToResponseWithValidExcuse() {
        Excuse excuse = Excuse.builder()
                .id(1L)
                .contexto(TEST_CONTEXTO_TEST)
                .causa(TEST_CAUSA_TEST)
                .consecuencia(TEST_CONSECUENCIA_TEST)
                .recomendacion(TEST_RECOMENDACION_TEST)
                .roleTarget(RoleType.DEV)
                .createdAt(LocalDateTime.now())
                .meme(TEST_MEME_DIVERTIDO)
                .ley(TEST_LEY_MURPHY)
                .build();

        ExcuseResponseDTO result = ExcuseMapper.toResponse(excuse);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(TEST_CONTEXTO_TEST, result.getContexto());
        assertEquals(TEST_CAUSA_TEST, result.getCausa());
        assertEquals(RoleType.DEV, result.getRoleTarget());
        assertNotNull(result.getMeme());
        assertEquals(TEST_MEME_DIVERTIDO, result.getMeme().getText());
        assertNotNull(result.getLey());
        assertEquals(TEST_LEY_MURPHY, result.getLey().getContent());
    }

    @Test
    @DisplayName("toResponse con excuse sin meme ni ley debe retornar DTO sin ellos")
    void testToResponseWithoutMemeAndLaw() {
        Excuse excuse = Excuse.builder()
                .id(2L)
                .contexto(CONTEXTO)
                .causa(CAUSA)
                .consecuencia(CONSECUENCIA)
                .recomendacion(RECOMENDACION)
                .createdAt(LocalDateTime.now())
                .build();

        ExcuseResponseDTO result = ExcuseMapper.toResponse(excuse);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertNull(result.getMeme());
        assertNull(result.getLey());
    }

    @Test
    @DisplayName("toResponse con excuse null debe retornar null")
    void testToResponseWithNull() {
        ExcuseResponseDTO result = ExcuseMapper.toResponse(null);
        assertNull(result);
    }
}
