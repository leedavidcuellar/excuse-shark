package com.excusasshark.service;

import com.excusasshark.dto.LawResponseDTO;
import com.excusasshark.model.Law;
import com.excusasshark.model.LawType;
import com.excusasshark.repository.LawRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.excusasshark.constants.TestConstants.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LawService Unit Tests")
class LawServiceTest {

    @Mock
    private LawRepository lawRepository;

    @InjectMocks
    private LawService lawService;

    private Law mockLaw;

    @BeforeEach
    void setUp() {
        mockLaw = Law.builder()
                .id(1L)
                .type(LawType.MURPHY)
                .content(TEST_LAW_CONTENT)
                .author(TEST_AUTHOR_MURPHY)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("create debe guardar y retornar ley")
    void testCreate() {
        when(lawRepository.save(any(Law.class))).thenReturn(mockLaw);

        Law result = lawService.create(mockLaw);

        assertNotNull(result);
        assertEquals(LawType.MURPHY, result.getType());
        verify(lawRepository, times(1)).save(any(Law.class));
    }

    @Test
    @DisplayName("getById con ID existente debe retornar DTO")
    void testGetByIdFound() {
        when(lawRepository.findById(1L)).thenReturn(Optional.of(mockLaw));

        LawResponseDTO result = lawService.getById(1L);

        assertNotNull(result);
        assertEquals(TEST_LAW_CONTENT, result.getContent());
        verify(lawRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById con ID inexistente debe retornar null")
    void testGetByIdNotFound() {
        when(lawRepository.findById(999L)).thenReturn(Optional.empty());

        LawResponseDTO result = lawService.getById(999L);

        assertNull(result);
        verify(lawRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("getAll debe retornar lista de DTOs")
    void testGetAll() {
        List<Law> laws = Arrays.asList(mockLaw);
        when(lawRepository.findAll()).thenReturn(laws);

        List<LawResponseDTO> result = lawService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(lawRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getActive debe retornar solo leyes activas")
    void testGetActive() {
        List<Law> activeLaws = Arrays.asList(mockLaw);
        when(lawRepository.findByActiveTrue()).thenReturn(activeLaws);

        List<LawResponseDTO> result = lawService.getActive();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(lawRepository, times(1)).findByActiveTrue();
    }

    @Test
    @DisplayName("getByType debe retornar leyes filtradas por tipo")
    void testGetByType() {
        List<Law> murphyLaws = Arrays.asList(mockLaw);
        when(lawRepository.findByType(LawType.MURPHY)).thenReturn(murphyLaws);

        List<LawResponseDTO> result = lawService.getByType(LawType.MURPHY);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(lawRepository, times(1)).findByType(LawType.MURPHY);
    }

    @Test
    @DisplayName("getRandomLaw debe retornar ley aleatoria")
    void testGetRandomLaw() {
        List<Law> laws = Arrays.asList(mockLaw);
        when(lawRepository.findByActiveTrue()).thenReturn(laws);

        Law result = lawService.getRandomLaw();

        assertNotNull(result);
        verify(lawRepository, times(1)).findByActiveTrue();
    }

    @Test
    @DisplayName("getRandomLaw con lista vacía debe retornar null")
    void testGetRandomLawEmpty() {
        when(lawRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

        Law result = lawService.getRandomLaw();

        assertNull(result);
        verify(lawRepository, times(1)).findByActiveTrue();
    }

    @Test
    @DisplayName("delete debe eliminar ley y retornar true")
    void testDeleteSuccess() {
        when(lawRepository.existsById(1L)).thenReturn(true);
        doNothing().when(lawRepository).deleteById(1L);

        boolean result = lawService.delete(1L);

        assertTrue(result);
        verify(lawRepository, times(1)).existsById(1L);
        verify(lawRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete con ID inexistente debe retornar false")
    void testDeleteNotFound() {
        when(lawRepository.existsById(999L)).thenReturn(false);

        boolean result = lawService.delete(999L);

        assertFalse(result);
        verify(lawRepository, times(1)).existsById(999L);
        verify(lawRepository, never()).deleteById(anyLong());
    }
}
