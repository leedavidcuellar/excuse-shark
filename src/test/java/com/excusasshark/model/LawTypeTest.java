package com.excusasshark.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LawType Enum Tests")
class LawTypeTest {

    @Test
    @DisplayName("valueOf debe retornar enum correcto")
    void testValueOf() {
        assertEquals(LawType.MURPHY, LawType.valueOf("MURPHY"));
        assertEquals(LawType.HOFSTADTER, LawType.valueOf("HOFSTADTER"));
        assertEquals(LawType.DILBERT, LawType.valueOf("DILBERT"));
    }

    @Test
    @DisplayName("values debe retornar todos los tipos de leyes")
    void testValues() {
        LawType[] types = LawType.values();
        assertTrue(types.length >= 3);
    }

    @Test
    @DisplayName("toString debe retornar nombre del enum")
    void testToString() {
        assertEquals("MURPHY", LawType.MURPHY.toString());
        assertEquals("HOFSTADTER", LawType.HOFSTADTER.toString());
    }
}
