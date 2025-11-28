package com.excusasshark.controller;

import com.excusasshark.dto.FragmentRequestDTO;
import com.excusasshark.dto.FragmentResponseDTO;
import com.excusasshark.model.FragmentType;
import com.excusasshark.service.FragmentService;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FragmentController Unit Tests")
class FragmentControllerTest {

    @Mock
    private FragmentService fragmentService;

    @InjectMocks
    private FragmentController fragmentController;

    private FragmentResponseDTO mockFragment;
    private FragmentRequestDTO mockRequest;

    @BeforeEach
    void setUp() {
        mockFragment = FragmentResponseDTO.builder()
                .id(1L)
                .type(FragmentType.CONTEXTO)
                .text(TEST_FRAGMENT)
                .source(TEST_SOURCE_VALUE)
                .category(TEST_CATEGORY_VALUE)
                .createdAt(LocalDateTime.now())
                .build();

        mockRequest = FragmentRequestDTO.builder()
                .type("CONTEXTO")
                .text(TEST_NEW_FRAGMENT)
                .source(TEST_SOURCE_VALUE)
                .category(TEST_CATEGORY_VALUE)
                .build();
    }

    @Test
    @DisplayName("getAllFragments debe retornar 200 OK")
    void testGetAllFragments() {
        List<FragmentResponseDTO> fragments = Arrays.asList(mockFragment);
        when(fragmentService.getAll()).thenReturn(fragments);

        ResponseEntity<List<FragmentResponseDTO>> response = fragmentController.getAllFragments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(fragmentService, times(1)).getAll();
    }

    @Test
    @DisplayName("getFragmentById con ID existente debe retornar 200 OK")
    void testGetFragmentByIdFound() {
        when(fragmentService.getById(1L)).thenReturn(mockFragment);

        ResponseEntity<FragmentResponseDTO> response = fragmentController.getFragmentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(fragmentService, times(1)).getById(1L);
    }

    @Test
    @DisplayName("getFragmentById con ID inexistente debe retornar 404")
    void testGetFragmentByIdNotFound() {
        when(fragmentService.getById(999L)).thenReturn(null);

        ResponseEntity<FragmentResponseDTO> response = fragmentController.getFragmentById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(fragmentService, times(1)).getById(999L);
    }

    @Test
    @DisplayName("getFragmentsByType debe retornar 200 OK")
    void testGetFragmentsByType() {
        List<FragmentResponseDTO> fragments = Arrays.asList(mockFragment);
        when(fragmentService.getByType("CONTEXTO")).thenReturn(fragments);

        ResponseEntity<List<FragmentResponseDTO>> response = fragmentController.getFragmentsByType("CONTEXTO");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(fragmentService, times(1)).getByType("CONTEXTO");
    }

    @Test
    @DisplayName("getActiveFragments debe retornar 200 OK")
    void testGetActiveFragments() {
        List<FragmentResponseDTO> fragments = Arrays.asList(mockFragment);
        when(fragmentService.getActive()).thenReturn(fragments);

        ResponseEntity<List<FragmentResponseDTO>> response = fragmentController.getActiveFragments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(fragmentService, times(1)).getActive();
    }

    @Test
    @DisplayName("createFragment debe retornar 201 Created")
    void testCreateFragment() {
        when(fragmentService.createFromDTOResponse(any(FragmentRequestDTO.class))).thenReturn(mockFragment);

        ResponseEntity<FragmentResponseDTO> response = fragmentController.createFragment(mockRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(fragmentService, times(1)).createFromDTOResponse(any(FragmentRequestDTO.class));
    }

    @Test
    @DisplayName("updateFragment con ID existente debe retornar 200 OK")
    void testUpdateFragmentFound() {
        when(fragmentService.updateFromDTO(eq(1L), any(FragmentRequestDTO.class))).thenReturn(mockFragment);

        ResponseEntity<FragmentResponseDTO> response = fragmentController.updateFragment(1L, mockRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(fragmentService, times(1)).updateFromDTO(eq(1L), any(FragmentRequestDTO.class));
    }

    @Test
    @DisplayName("updateFragment con ID inexistente debe retornar 404")
    void testUpdateFragmentNotFound() {
        when(fragmentService.updateFromDTO(eq(999L), any(FragmentRequestDTO.class))).thenReturn(null);

        ResponseEntity<FragmentResponseDTO> response = fragmentController.updateFragment(999L, mockRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(fragmentService, times(1)).updateFromDTO(eq(999L), any(FragmentRequestDTO.class));
    }

    @Test
    @DisplayName("deleteFragment con ID existente debe retornar 204 No Content")
    void testDeleteFragmentFound() {
        when(fragmentService.delete(1L)).thenReturn(true);

        ResponseEntity<Void> response = fragmentController.deleteFragment(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(fragmentService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("deleteFragment con ID inexistente debe retornar 404")
    void testDeleteFragmentNotFound() {
        when(fragmentService.delete(999L)).thenReturn(false);

        ResponseEntity<Void> response = fragmentController.deleteFragment(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(fragmentService, times(1)).delete(999L);
    }
}
