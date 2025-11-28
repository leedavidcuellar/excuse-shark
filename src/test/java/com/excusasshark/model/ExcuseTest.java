package com.excusasshark.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Excuse Entity Tests")
class ExcuseTest {

    @Test
    @DisplayName("Builder debe construir Excuse correctamente")
    void testBuilder() {
        Excuse excuse = Excuse.builder()
                .id(1L)
                .contexto(CONTEXTO)
                .causa(CAUSA)
                .consecuencia(CONSECUENCIA)
                .recomendacion(RECOMENDACION)
                .roleTarget(RoleType.DEV)
                .build();

        assertEquals(1L, excuse.getId());
        assertEquals(CONTEXTO, excuse.getContexto());
        assertEquals(CAUSA, excuse.getCausa());
        assertEquals(RoleType.DEV, excuse.getRoleTarget());
    }

    @Test
    @DisplayName("Getters y Setters deben funcionar correctamente")
    void testGettersSetters() {
        Excuse excuse = new Excuse();
        excuse.setId(1L);
        excuse.setContexto(TEST_EXCUSE_CONTEXTO);
        excuse.setCausa(TEST_EXCUSE_CAUSA);
        excuse.setRoleTarget(RoleType.PM);

        assertEquals(1L, excuse.getId());
        assertEquals(TEST_EXCUSE_CONTEXTO, excuse.getContexto());
        assertEquals(TEST_EXCUSE_CAUSA, excuse.getCausa());
        assertEquals(RoleType.PM, excuse.getRoleTarget());
    }
}
