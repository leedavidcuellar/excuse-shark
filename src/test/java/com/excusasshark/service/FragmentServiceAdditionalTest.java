package com.excusasshark.service;

import com.excusasshark.dto.FragmentRequestDTO;
import com.excusasshark.dto.FragmentResponseDTO;
import com.excusasshark.model.Fragment;
import com.excusasshark.model.FragmentType;
import com.excusasshark.repository.FragmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FragmentService Additional Tests")
class FragmentServiceAdditionalTest {

    @Mock
    private FragmentRepository fragmentRepository;

    @InjectMocks
    private FragmentService fragmentService;

    @Test
    @DisplayName("createFromDTO crea y retorna entidad")
    void createFromDTOTest() {
        FragmentRequestDTO dto = FragmentRequestDTO.builder()
                .type(TEXT_CAUSA)
                .text(TEST_TEXTO_CAUSA)
                .source(FUENTE)
                .category(CAT)
                .build();

        when(fragmentRepository.save(any(Fragment.class))).thenAnswer(invocation -> {
            Fragment f = invocation.getArgument(0);
            f.setId(99L);
            return f;
        });

        Fragment created = fragmentService.createFromDTO(dto);
        assertNotNull(created.getId());
        assertEquals(FragmentType.CAUSA, created.getType());
        verify(fragmentRepository).save(any(Fragment.class));
    }

    @Test
    @DisplayName("createFromDTOResponse retorna DTO con id")
    void createFromDTOResponseTest() {
        FragmentRequestDTO dto = FragmentRequestDTO.builder()
                .type(TEXT_CONTEXTO)
                .text(TEST_TEXTO_CONTEXTO)
                .source(FUENTE2)
                .category(CAT2)
                .build();

        when(fragmentRepository.save(any(Fragment.class))).thenAnswer(invocation -> {
            Fragment f = invocation.getArgument(0);
            f.setId(100L);
            return f;
        });

        FragmentResponseDTO response = fragmentService.createFromDTOResponse(dto);
        assertEquals(100L, response.getId());
        assertEquals(FragmentType.CONTEXTO, response.getType());
    }

    @Test
    @DisplayName("getAll retorna lista mapeada")
    void getAllTest() {
        Fragment f1 = Fragment.builder().id(1L).type(FragmentType.CONSECUENCIA).text("T1").build();
        Fragment f2 = Fragment.builder().id(2L).type(FragmentType.RECOMENDACION).text("T2").build();
        when(fragmentRepository.findAll()).thenReturn(List.of(f1, f2));

        List<FragmentResponseDTO> list = fragmentService.getAll();
        assertEquals(2, list.size());
        assertEquals(FragmentType.CONSECUENCIA, list.get(0).getType());
        verify(fragmentRepository).findAll();
    }

    @Test
    @DisplayName("updateFromDTO actualiza y retorna DTO")
    void updateFromDTOTest() {
        Fragment existing = Fragment.builder().id(5L).text("Old").source("S").category("C").build();
        when(fragmentRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(fragmentRepository.save(existing)).thenReturn(existing);

        FragmentRequestDTO dto = FragmentRequestDTO.builder().text("New").source("S2").build();
        FragmentResponseDTO updated = fragmentService.updateFromDTO(5L, dto);
        assertNotNull(updated);
        assertEquals("New", existing.getText());
        assertEquals("S2", existing.getSource());
    }

    @Test
    @DisplayName("getRandomFragment retorna null si lista vacía")
    void getRandomFragmentEmpty() {
        when(fragmentRepository.findByTypeAndActiveTrue(FragmentType.CAUSA)).thenReturn(List.of());
        assertNull(fragmentService.getRandomFragment(FragmentType.CAUSA));
    }

    @Test
    @DisplayName("getRandomFragment retorna único elemento")
    void getRandomFragmentSingle() {
        Fragment unico = Fragment.builder().id(7L).type(FragmentType.CAUSA).text("Texto").build();
        when(fragmentRepository.findByTypeAndActiveTrue(FragmentType.CAUSA)).thenReturn(List.of(unico));
        Fragment result = fragmentService.getRandomFragment(FragmentType.CAUSA);
        assertEquals(7L, result.getId());
    }
}
