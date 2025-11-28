package com.excusasshark.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Excuse onCreate PrePersist Test")
class ExcuseOnCreateTest {

    @Test
    @DisplayName("onCreate establece createdAt a valor actual")
    void onCreateSetsCreatedAt() throws Exception {
        Excuse excuse = new Excuse();
        excuse.setContexto("C");
        excuse.setCausa(CAUSA);
        excuse.setConsecuencia("Cons");
        excuse.setRecomendacion("Rec");
        // createdAt inicial es ahora por builder default; forzamos null para validar asignación
        excuse.setCreatedAt(null);

        Method m = Excuse.class.getDeclaredMethod("onCreate");
        m.setAccessible(true);
        m.invoke(excuse);

        assertNotNull(excuse.getCreatedAt());
        assertTrue(excuse.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(2))); // margen
    }
}
