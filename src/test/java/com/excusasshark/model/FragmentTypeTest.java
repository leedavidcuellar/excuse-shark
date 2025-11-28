package com.excusasshark.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FragmentType Enum Tests")
class FragmentTypeTest {

    @Test
    @DisplayName("valueOf debe retornar enum correcto")
    void testValueOf() {
        assertEquals(FragmentType.CONTEXTO, FragmentType.valueOf("CONTEXTO"));
        assertEquals(FragmentType.CAUSA, FragmentType.valueOf("CAUSA"));
        assertEquals(FragmentType.CONSECUENCIA, FragmentType.valueOf("CONSECUENCIA"));
        assertEquals(FragmentType.RECOMENDACION, FragmentType.valueOf("RECOMENDACION"));
    }

    @Test
    @DisplayName("values debe retornar todos los tipos")
    void testValues() {
        FragmentType[] types = FragmentType.values();
        assertEquals(4, types.length);
    }

    @Test
    @DisplayName("toString debe retornar nombre del enum")
    void testToString() {
        assertEquals("CONTEXTO", FragmentType.CONTEXTO.toString());
        assertEquals("CAUSA", FragmentType.CAUSA.toString());
    }
}
