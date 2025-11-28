package com.excusasshark.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Meme Entity Tests")
class MemeTest {

    @Test
    @DisplayName("onCreate debe establecer timestamps")
    void testOnCreate() {
        Meme meme = new Meme();
        meme.onCreate();

        assertNotNull(meme.getCreatedAt());
        assertNotNull(meme.getUpdatedAt());
    }

    @Test
    @DisplayName("onUpdate debe actualizar updatedAt")
    void testOnUpdate() {
        Meme meme = new Meme();
        meme.onCreate();
        LocalDateTime originalUpdated = meme.getUpdatedAt();
        
        meme.onUpdate();

        assertTrue(meme.getUpdatedAt().isAfter(originalUpdated) || meme.getUpdatedAt().isEqual(originalUpdated));
    }

    @Test
    @DisplayName("Builder debe construir Meme correctamente")
    void testBuilder() {
        Meme meme = Meme.builder()
                .id(1L)
                .text(TEST_MEME)
                .author(TEST_AUTHOR)
                .source(TEST_SOURCE_VALUE)
                .category(TEST_CATEGORY_VALUE)
                .active(true)
                .build();

        assertEquals(1L, meme.getId());
        assertEquals(TEST_MEME, meme.getText());
        assertEquals(TEST_AUTHOR, meme.getAuthor());
        assertTrue(meme.getActive());
    }

    @Test
    @DisplayName("Getters y Setters deben funcionar correctamente")
    void testGettersSetters() {
        Meme meme = new Meme();
        meme.setId(1L);
        meme.setText(TEST_MEME_TEXT);
        meme.setCategory(TEST_HUMOR);
        meme.setActive(false);

        assertEquals(1L, meme.getId());
        assertEquals(TEST_MEME_TEXT, meme.getText());
        assertEquals(TEST_HUMOR, meme.getCategory());
        assertFalse(meme.getActive());
    }
}
