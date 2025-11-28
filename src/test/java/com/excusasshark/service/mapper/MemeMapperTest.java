package com.excusasshark.service.mapper;

import com.excusasshark.dto.MemeResponseDTO;
import com.excusasshark.model.Meme;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MemeMapper Tests")
class MemeMapperTest {

    @Test
    @DisplayName("toResponse con meme válido debe retornar DTO completo")
    void testToResponseWithValidMeme() {
        Meme meme = Meme.builder()
                .id(1L)
                .text(TEST_MEME_DIVERTIDO)
                .author(TEST_AUTHOR)
                .source(TEST_TWITTER)
                .category(TEST_HUMOR)
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();

        MemeResponseDTO result = MemeMapper.toResponse(meme);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(TEST_MEME_DIVERTIDO, result.getText());
        assertEquals(TEST_AUTHOR, result.getAuthor());
        assertEquals(TEST_TWITTER, result.getSource());
        assertEquals(TEST_HUMOR, result.getCategory());
        assertTrue(result.getActive());
    }

    @Test
    @DisplayName("toResponse con meme null debe retornar null")
    void testToResponseWithNull() {
        MemeResponseDTO result = MemeMapper.toResponse(null);
        assertNull(result);
    }

    @Test
    @DisplayName("toResponse con campos opcionales nulos debe funcionar")
    void testToResponseWithNullOptionalFields() {
        Meme meme = Meme.builder()
                .id(2L)
                .text(TEST_SOLO_TEXTO)
                .active(false)
                .build();

        MemeResponseDTO result = MemeMapper.toResponse(meme);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(TEST_SOLO_TEXTO, result.getText());
        assertNull(result.getAuthor());
        assertFalse(result.getActive());
    }
}
