package com.excusasshark.controller;

import com.excusasshark.dto.ExcuseResponseDTO;
import com.excusasshark.dto.UltraSharkExcuseDTO;
import com.excusasshark.dto.MemeResponseDTO;
import com.excusasshark.dto.LawResponseDTO;
import com.excusasshark.model.RoleType;
import com.excusasshark.service.ExcuseGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExcuseController Unit Tests")
class ExcuseControllerTest {

    @Mock
    private ExcuseGeneratorService excuseGeneratorService;

    @InjectMocks
    private ExcuseController excuseController;

    private ExcuseResponseDTO mockExcuse;

    @BeforeEach
    void setUp() {
        mockExcuse = ExcuseResponseDTO.builder()
                .id(1L)
                .contexto(TEST_EXCUSE_CONTEXTO)
                .causa(TEST_EXCUSE_CAUSA)
                .consecuencia(TEST_EXCUSE_CONSECUENCIA)
                .recomendacion(TEST_EXCUSE_RECOMENDACION)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("generateRandomExcuse debe retornar 200 OK")
    void testGenerateRandomExcuse() {
        when(excuseGeneratorService.generateRandomExcuse()).thenReturn(mockExcuse);

        ResponseEntity<ExcuseResponseDTO> response = excuseController.generateRandomExcuse();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(TEST_EXCUSE_CONTEXTO, response.getBody().getContexto());
        verify(excuseGeneratorService, times(1)).generateRandomExcuse();
    }

    @Test
    @DisplayName("getDailyExcuse debe retornar 200 OK")
    void testGetDailyExcuse() {
        when(excuseGeneratorService.getDailyExcuse()).thenReturn(mockExcuse);

        ResponseEntity<ExcuseResponseDTO> response = excuseController.getDailyExcuse();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(excuseGeneratorService, times(1)).getDailyExcuse();
    }

    @Test
    @DisplayName("generateExcuseWithMeme debe retornar 200 OK con meme")
    void testGenerateExcuseWithMeme() {
        MemeResponseDTO meme = MemeResponseDTO.builder().text(TEST_MEME).build();
        ExcuseResponseDTO excuseWithMeme = ExcuseResponseDTO.builder()
                .id(2L)
                .contexto(CONTEXTO)
                .causa(CAUSA)
                .consecuencia(CONSECUENCIA)
                .recomendacion(RECOMENDACION)
                .meme(meme)
                .createdAt(LocalDateTime.now())
                .build();
        when(excuseGeneratorService.generateExcuseWithMeme()).thenReturn(excuseWithMeme);

        ResponseEntity<ExcuseResponseDTO> response = excuseController.generateExcuseWithMeme();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getMeme());
        verify(excuseGeneratorService, times(1)).generateExcuseWithMeme();
    }

    @Test
    @DisplayName("generateExcuseWithLaw debe retornar 200 OK con ley")
    void testGenerateExcuseWithLaw() {
        LawResponseDTO law = LawResponseDTO.builder().content(TEST_LEY_MURPHY).build();
        ExcuseResponseDTO excuseWithLaw = ExcuseResponseDTO.builder()
                .id(3L)
                .contexto(CONTEXTO)
                .causa(CAUSA)
                .consecuencia(CONSECUENCIA)
                .recomendacion(RECOMENDACION)
                .ley(law)
                .createdAt(LocalDateTime.now())
                .build();
        when(excuseGeneratorService.generateExcuseWithLaw()).thenReturn(excuseWithLaw);

        ResponseEntity<ExcuseResponseDTO> response = excuseController.generateExcuseWithLaw();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getLey());
        verify(excuseGeneratorService, times(1)).generateExcuseWithLaw();
    }

    @Test
    @DisplayName("generateUltraSharkExcuse debe retornar 200 OK")
    void testGenerateUltraSharkExcuse() {
        UltraSharkExcuseDTO ultraShark = UltraSharkExcuseDTO.builder()
                .excusa(mockExcuse)
                .meme(TEST_MEME_ULTRA)
                .ley(TEST_LEY_ULTRA)
                .build();
        when(excuseGeneratorService.generateUltraSharkExcuse()).thenReturn(ultraShark);

        ResponseEntity<UltraSharkExcuseDTO> response = excuseController.generateUltraSharkExcuse();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(TEST_MEME_ULTRA, response.getBody().getMeme());
        verify(excuseGeneratorService, times(1)).generateUltraSharkExcuse();
    }

    @Test
    @DisplayName("generateExcuseForRole con rol válido debe retornar 200 OK")
    void testGenerateExcuseForRoleValid() {
        ExcuseResponseDTO excuseForRole = ExcuseResponseDTO.builder()
                .id(4L)
                .contexto(CONTEXTO)
                .causa(CAUSA)
                .consecuencia(CONSECUENCIA)
                .recomendacion(RECOMENDACION)
                .roleTarget(RoleType.DEV)
                .createdAt(LocalDateTime.now())
                .build();
        when(excuseGeneratorService.generateExcuseForRole(any(RoleType.class))).thenReturn(excuseForRole);

        ResponseEntity<ExcuseResponseDTO> response = excuseController.generateExcuseForRole("DEV");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(RoleType.DEV, response.getBody().getRoleTarget());
        verify(excuseGeneratorService, times(1)).generateExcuseForRole(RoleType.DEV);
    }

    @Test
    @DisplayName("generateExcuseForRole con rol inválido debe retornar 400 Bad Request")
    void testGenerateExcuseForRoleInvalid() {
        ResponseEntity<ExcuseResponseDTO> response = excuseController.generateExcuseForRole("INVALID");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(excuseGeneratorService, never()).generateExcuseForRole(any());
    }

    @Test
    @DisplayName("getAllExcuses debe retornar 200 OK con lista")
    void testGetAllExcuses() {
        List<ExcuseResponseDTO> excuses = Arrays.asList(mockExcuse);
        when(excuseGeneratorService.getAll()).thenReturn(excuses);

        ResponseEntity<List<ExcuseResponseDTO>> response = excuseController.getAllExcuses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(excuseGeneratorService, times(1)).getAll();
    }

    @Test
    @DisplayName("getExcuseById con ID existente debe retornar 200 OK")
    void testGetExcuseByIdFound() {
        when(excuseGeneratorService.getById(1L)).thenReturn(mockExcuse);

        ResponseEntity<ExcuseResponseDTO> response = excuseController.getExcuseById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(excuseGeneratorService, times(1)).getById(1L);
    }

    @Test
    @DisplayName("getExcuseById con ID inexistente debe retornar 404 Not Found")
    void testGetExcuseByIdNotFound() {
        when(excuseGeneratorService.getById(999L)).thenReturn(null);

        ResponseEntity<ExcuseResponseDTO> response = excuseController.getExcuseById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(excuseGeneratorService, times(1)).getById(999L);
    }
}
