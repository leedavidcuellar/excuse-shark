package com.excusasshark.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Fragment Entity Tests")
class FragmentTest {

    @Test
    @DisplayName("onCreate debe establecer timestamps")
    void testOnCreate() {
        Fragment fragment = new Fragment();
        fragment.onCreate();

        assertNotNull(fragment.getCreatedAt());
        assertNotNull(fragment.getUpdatedAt());
    }

    @Test
    @DisplayName("onUpdate debe actualizar updatedAt")
    void testOnUpdate() {
        Fragment fragment = new Fragment();
        fragment.onCreate();
        LocalDateTime originalUpdated = fragment.getUpdatedAt();
        
        fragment.onUpdate();

        assertTrue(fragment.getUpdatedAt().isAfter(originalUpdated) || fragment.getUpdatedAt().isEqual(originalUpdated));
    }

    @Test
    @DisplayName("Builder debe construir Fragment correctamente")
    void testBuilder() {
        Fragment fragment = Fragment.builder()
                .id(1L)
                .type(FragmentType.CONTEXTO)
                .text(TEST_TEXT)
                .source(TEST_SOURCE_VALUE)
                .category(TEST_CATEGORY_VALUE)
                .active(true)
                .build();

        assertEquals(1L, fragment.getId());
        assertEquals(FragmentType.CONTEXTO, fragment.getType());
        assertEquals(TEST_TEXT, fragment.getText());
        assertTrue(fragment.getActive());
    }

    @Test
    @DisplayName("Getters y Setters deben funcionar correctamente")
    void testGettersSetters() {
        Fragment fragment = new Fragment();
        fragment.setId(1L);
        fragment.setType(FragmentType.CAUSA);
        fragment.setText(TEST_CAUSA_TEST);
        fragment.setActive(false);

        assertEquals(1L, fragment.getId());
        assertEquals(FragmentType.CAUSA, fragment.getType());
        assertEquals(TEST_CAUSA_TEST, fragment.getText());
        assertFalse(fragment.getActive());
    }
}
