package com.excusasshark.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RoleType Enum Tests")
class RoleTypeTest {

    @Test
    @DisplayName("valueOf debe retornar enum correcto")
    void testValueOf() {
        assertEquals(RoleType.DEV, RoleType.valueOf("DEV"));
        assertEquals(RoleType.PM, RoleType.valueOf("PM"));
        assertEquals(RoleType.QA, RoleType.valueOf("QA"));
        assertEquals(RoleType.DEVOPS, RoleType.valueOf("DEVOPS"));
    }

    @Test
    @DisplayName("values debe retornar todos los roles")
    void testValues() {
        RoleType[] roles = RoleType.values();
        assertTrue(roles.length >= 4);
    }

    @Test
    @DisplayName("toString debe retornar nombre del enum")
    void testToString() {
        assertEquals("DEV", RoleType.DEV.toString());
        assertEquals("PM", RoleType.PM.toString());
    }
}
