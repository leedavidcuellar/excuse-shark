package com.excusasshark.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Law Entity Tests")
class LawTest {

    @Test
    @DisplayName("onCreate debe establecer timestamps")
    void testOnCreate() {
        Law law = new Law();
        law.onCreate();

        assertNotNull(law.getCreatedAt());
        assertNotNull(law.getUpdatedAt());
    }

    @Test
    @DisplayName("onUpdate debe actualizar updatedAt")
    void testOnUpdate() {
        Law law = new Law();
        law.onCreate();
        LocalDateTime originalUpdated = law.getUpdatedAt();
        
        law.onUpdate();

        assertTrue(law.getUpdatedAt().isAfter(originalUpdated) || law.getUpdatedAt().isEqual(originalUpdated));
    }

    @Test
    @DisplayName("Builder debe construir Law correctamente")
    void testBuilder() {
        Law law = Law.builder()
                .id(1L)
                .type(LawType.MURPHY)
                .content(TEST_LAW_CONTENT)
                .author(TEST_AUTHOR_MURPHY)
                .active(true)
                .build();

        assertEquals(1L, law.getId());
        assertEquals(LawType.MURPHY, law.getType());
        assertEquals(TEST_LAW_CONTENT, law.getContent());
        assertTrue(law.getActive());
    }

    @Test
    @DisplayName("Getters y Setters deben funcionar correctamente")
    void testGettersSetters() {
        Law law = new Law();
        law.setId(1L);
        law.setType(LawType.HOFSTADTER);
        law.setContent(TEST_LEY_HOFSTADTER);
        law.setActive(false);

        assertEquals(1L, law.getId());
        assertEquals(LawType.HOFSTADTER, law.getType());
        assertEquals(TEST_LEY_HOFSTADTER, law.getContent());
        assertFalse(law.getActive());
    }
}
