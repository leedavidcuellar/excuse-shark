package com.excusasshark.service;

import com.excusasshark.dto.MemeResponseDTO;
import com.excusasshark.model.Meme;
import com.excusasshark.repository.MemeRepository;
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
@DisplayName("MemeService Unit Tests")
class MemeServiceTest {

    @Mock
    private MemeRepository memeRepository;

    @InjectMocks
    private MemeService memeService;

    private Meme mockMeme;

    @BeforeEach
    void setUp() {
        mockMeme = Meme.builder()
                .id(1L)
                .text(TEST_MEME)
                .author(TEST_AUTHOR)
                .source(TEST_SOURCE_VALUE)
                .category(TEST_CATEGORY_VALUE)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("create debe guardar y retornar meme")
    void testCreate() {
        when(memeRepository.save(any(Meme.class))).thenReturn(mockMeme);

        Meme result = memeService.create(mockMeme);

        assertNotNull(result);
        assertEquals(TEST_MEME, result.getText());
        verify(memeRepository, times(1)).save(any(Meme.class));
    }

    @Test
    @DisplayName("getById con ID existente debe retornar DTO")
    void testGetByIdFound() {
        when(memeRepository.findById(1L)).thenReturn(Optional.of(mockMeme));

        MemeResponseDTO result = memeService.getById(1L);

        assertNotNull(result);
        assertEquals(TEST_MEME, result.getText());
        verify(memeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById con ID inexistente debe retornar null")
    void testGetByIdNotFound() {
        when(memeRepository.findById(999L)).thenReturn(Optional.empty());

        MemeResponseDTO result = memeService.getById(999L);

        assertNull(result);
        verify(memeRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("getAll debe retornar lista de DTOs")
    void testGetAll() {
        List<Meme> memes = Arrays.asList(mockMeme);
        when(memeRepository.findAll()).thenReturn(memes);

        List<MemeResponseDTO> result = memeService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(memeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getActive debe retornar solo memes activos")
    void testGetActive() {
        List<Meme> activeMemes = Arrays.asList(mockMeme);
        when(memeRepository.findByActiveTrue()).thenReturn(activeMemes);

        List<MemeResponseDTO> result = memeService.getActive();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(memeRepository, times(1)).findByActiveTrue();
    }

    @Test
    @DisplayName("getRandomMeme debe retornar meme aleatorio")
    void testGetRandomMeme() {
        List<Meme> memes = Arrays.asList(mockMeme);
        when(memeRepository.findByActiveTrue()).thenReturn(memes);

        Meme result = memeService.getRandomMeme();

        assertNotNull(result);
        verify(memeRepository, times(1)).findByActiveTrue();
    }

    @Test
    @DisplayName("getRandomMeme con lista vacía debe retornar null")
    void testGetRandomMemeEmpty() {
        when(memeRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

        Meme result = memeService.getRandomMeme();

        assertNull(result);
        verify(memeRepository, times(1)).findByActiveTrue();
    }

    @Test
    @DisplayName("delete debe eliminar meme y retornar true")
    void testDeleteSuccess() {
        when(memeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(memeRepository).deleteById(1L);

        boolean result = memeService.delete(1L);

        assertTrue(result);
        verify(memeRepository, times(1)).existsById(1L);
        verify(memeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete con ID inexistente debe retornar false")
    void testDeleteNotFound() {
        when(memeRepository.existsById(999L)).thenReturn(false);

        boolean result = memeService.delete(999L);

        assertFalse(result);
        verify(memeRepository, times(1)).existsById(999L);
        verify(memeRepository, never()).deleteById(anyLong());
    }
}
