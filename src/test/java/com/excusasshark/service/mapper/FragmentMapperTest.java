package com.excusasshark.service.mapper;

import com.excusasshark.dto.FragmentRequestDTO;
import com.excusasshark.dto.FragmentResponseDTO;
import com.excusasshark.model.Fragment;
import com.excusasshark.model.FragmentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.excusasshark.constants.TestConstants.*;

@DisplayName("FragmentMapper Tests")
class FragmentMapperTest {

    @Test
    @DisplayName("toEntity debe mapear correctamente tipo válido")
    void toEntityValidType() {
        FragmentRequestDTO dto = FragmentRequestDTO.builder()
                .type(TEST_TYPE_CONTEXTO)
                .text(TEST_TEXT)
                .source(TEST_SOURCE)
                .category(TEST_CATEGORY)
                .build();

        Fragment entity = FragmentMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(FragmentType.CONTEXTO, entity.getType());
        assertEquals(TEST_TEXT, entity.getText());
        assertEquals(TEST_SOURCE, entity.getSource());
        assertEquals(TEST_CATEGORY, entity.getCategory());
    }

    @Test
    @DisplayName("toEntity debe retornar null si dto es null")
    void toEntityNull() {
        assertNull(FragmentMapper.toEntity(null));
    }

    @Test
    @DisplayName("toEntity ignora tipo inválido dejando type en null")
    void toEntityInvalidType() {
        FragmentRequestDTO dto = FragmentRequestDTO.builder()
                .type(TEST_TYPE_INVALID)
                .text(TEST_TEXT)
                .source(TEST_SOURCE)
                .category(TEST_CATEGORY)
                .build();

        Fragment entity = FragmentMapper.toEntity(dto);
        assertNull(entity.getType());
        assertEquals(TEST_TEXT, entity.getText());
    }

    @Test
    @DisplayName("toResponse devuelve null si fragment es null")
    void toResponseNull() {
        assertNull(FragmentMapper.toResponse(null));
    }

    @Test
    @DisplayName("toResponse mapea correctamente todos los campos")
    void toResponseValid() {
        Fragment fragment = Fragment.builder()
                .id(10L)
                .type(FragmentType.CAUSA)
                .text(TEST_TEXT_CAUSA)
                .source(TEST_SOURCE)
                .category(TEST_CATEGORY_SHORT)
                .build();

        FragmentResponseDTO response = FragmentMapper.toResponse(fragment);
        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals(FragmentType.CAUSA, response.getType());
        assertEquals(TEST_TEXT_CAUSA, response.getText());
        assertEquals(TEST_SOURCE, response.getSource());
        assertEquals(TEST_CATEGORY_SHORT, response.getCategory());
    }

    @Test
    @DisplayName("updateEntity no hace nada si dto es null")
    void updateEntityNullDto() {
        Fragment existing = Fragment.builder().text("Orig").source("S").category("C").build();
        FragmentMapper.updateEntity(null, existing);
        assertEquals("Orig", existing.getText());
    }

    @Test
    @DisplayName("updateEntity no hace nada si existing es null")
    void updateEntityNullExisting() {
        FragmentRequestDTO dto = FragmentRequestDTO.builder().text("Nuevo").build();
        assertDoesNotThrow(() -> FragmentMapper.updateEntity(dto, null));
    }

    @Test
    @DisplayName("updateEntity actualiza solo campos no nulos")
    void updateEntityPartial() {
        FragmentRequestDTO dto = FragmentRequestDTO.builder()
                .text("NuevoTexto")
                .build();
        Fragment existing = Fragment.builder()
                .text("Orig")
                .source(FUENTE)
                .category(CAT)
                .build();

        FragmentMapper.updateEntity(dto, existing);
        assertEquals("NuevoTexto", existing.getText());
        assertEquals(FUENTE, existing.getSource());
        assertEquals(CAT, existing.getCategory());
    }

    @Test
    @DisplayName("updateEntity actualiza todos los campos disponibles")
    void updateEntityAll() {
        FragmentRequestDTO dto = FragmentRequestDTO.builder()
                .text("T")
                .source("S2")
                .category("C2")
                .build();
        Fragment existing = Fragment.builder()
                .text("Orig")
                .source(FUENTE)
                .category(CAT)
                .build();

        FragmentMapper.updateEntity(dto, existing);
        assertEquals("T", existing.getText());
        assertEquals("S2", existing.getSource());
        assertEquals("C2", existing.getCategory());
    }

    @Test
    @DisplayName("updateEntity no actualiza text si es null en el DTO")
    void updateEntityTextNull() {
        FragmentRequestDTO dto = FragmentRequestDTO.builder()
                .text(null) // text es null
                .source("NuevaFuente")
                .category("NuevaCat")
                .build();
        Fragment existing = Fragment.builder()
                .text("TextoOriginal")
                .source("FuenteOrig")
                .category("CatOrig")
                .build();

        FragmentMapper.updateEntity(dto, existing);
        
        // text NO debe cambiar porque es null en el DTO (línea 59)
        assertEquals("TextoOriginal", existing.getText());
        // source y category SÍ deben cambiar
        assertEquals("NuevaFuente", existing.getSource());
        assertEquals("NuevaCat", existing.getCategory());
    }
}
