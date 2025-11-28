package com.excusasshark.service;

import com.excusasshark.dto.FragmentResponseDTO;
import com.excusasshark.model.Fragment;
import com.excusasshark.model.FragmentType;
import com.excusasshark.repository.FragmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.excusasshark.constants.TestConstants.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Tests unitarios para FragmentService
 * Verifica la lógica CRUD de fragmentos
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FragmentService Tests")
class FragmentServiceTest {

    @Mock
    private FragmentRepository fragmentRepository;

    @InjectMocks
    private FragmentService fragmentService;

    private Fragment testFragment;

    @BeforeEach
    void setUp() {
        testFragment = Fragment.builder()
                .id(1L)
                .type(FragmentType.CONTEXTO)
                .text(TEST_CONTEXT_FRAGMENT)
                .source(TEST_SOURCE_VALUE)
                .category(TEST_CATEGORY_VALUE)
                .build();
    }

    @Test
    @DisplayName("Debe retornar fragmento por ID")
    void testGetFragmentById() {
        // Arrange
        when(fragmentRepository.findById(1L)).thenReturn(Optional.of(testFragment));

        // Act
        FragmentResponseDTO result = fragmentService.getById(1L);

        // Assert
        assertNotNull(result, "El fragmento no debe ser nulo");
        assertEquals(TEST_CONTEXT_FRAGMENT, result.getText());
        assertEquals(FragmentType.CONTEXTO, result.getType());
    }

    @Test
    @DisplayName("Debe retornar null si fragmento no existe")
    void testGetFragmentByIdNotFound() {
        // Arrange
        when(fragmentRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        FragmentResponseDTO result = fragmentService.getById(99L);

        // Assert
        assertNull(result, "Debe retornar null si no existe el fragmento");
    }

    @Test
    @DisplayName("Debe crear fragmento exitosamente")
    void testCreateFragment() {
        // Arrange
        Fragment newFragment = Fragment.builder()
                .type(FragmentType.CAUSA)
                .text(NEW_FRAGMENT)
                .source(TEST_SOURCE_UNIT)
                .category(DEVELOPMENT)
                .build();

        when(fragmentRepository.save(any(Fragment.class))).thenReturn(newFragment);

        // Act
        Fragment result = fragmentService.create(newFragment);

        // Assert
        assertNotNull(result, "El fragmento creado no debe ser nulo");
        assertEquals("New fragment", result.getText());
    }

    @Test
    @DisplayName("Debe obtener fragmentos por tipo")
    void testGetFragmentsByType() {
        // Act
        var result = fragmentService.getByType("CONTEXTO");

        // Assert
        assertNotNull(result, "La lista no debe ser nula");
    }

    @Test
    @DisplayName("Debe retornar fragmentos activos")
    void testGetActiveFragments() {
        // Act
        var result = fragmentService.getActive();

        // Assert
        assertNotNull(result, "La lista de fragmentos activos no debe ser nula");
    }

    @Test
    @DisplayName("Debe eliminar fragmento")
    void testDeleteFragment() {
        // Arrange
        when(fragmentRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = fragmentService.delete(1L);

        // Assert
        assertTrue(result, "La eliminación debe retornar true");
    }

    @Test
    @DisplayName("Debe retornar false si intenta eliminar fragmento inexistente")
    void testDeleteFragmentNotFound() {
        // Arrange
        when(fragmentRepository.existsById(99L)).thenReturn(false);

        // Act
        boolean result = fragmentService.delete(99L);

        // Assert
        assertFalse(result, "Debe retornar false si no existe el fragmento");
    }
}
