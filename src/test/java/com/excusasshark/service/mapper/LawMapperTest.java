package com.excusasshark.service.mapper;

import com.excusasshark.dto.LawResponseDTO;
import com.excusasshark.model.Law;
import com.excusasshark.model.LawType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LawMapper Tests")
class LawMapperTest {

    @Test
    @DisplayName("toResponse con law válida debe retornar DTO completo")
    void testToResponseWithValidLaw() {
        Law law = Law.builder()
                .id(1L)
                .type(LawType.MURPHY)
                .content(TEST_LAW_CONTENT)
                .author(TEST_AUTHOR_MURPHY)
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();

        LawResponseDTO result = LawMapper.toResponse(law);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("MURPHY", result.getType());
        assertEquals(TEST_LAW_CONTENT, result.getContent());
        assertEquals(TEST_AUTHOR_MURPHY, result.getAuthor());
        assertTrue(result.getActive());
    }

    @Test
    @DisplayName("toResponse con law null debe retornar null")
    void testToResponseWithNull() {
        LawResponseDTO result = LawMapper.toResponse(null);
        assertNull(result);
    }

    @Test
    @DisplayName("toResponse con type null debe manejar correctamente")
    void testToResponseWithNullType() {
        Law law = Law.builder()
                .id(2L)
                .content(TEST_LEY_SIN_TIPO)
                .active(false)
                .build();

        LawResponseDTO result = LawMapper.toResponse(law);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertNull(result.getType());
        assertEquals(TEST_LEY_SIN_TIPO, result.getContent());
        assertFalse(result.getActive());
    }
}
