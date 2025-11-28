package com.excusasshark.service;

import com.excusasshark.dto.FragmentRequestDTO;
import com.excusasshark.model.Fragment;
import com.excusasshark.model.FragmentType;
import com.excusasshark.repository.FragmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FragmentService Branch Coverage Tests")
class FragmentServiceBranchesTest {

    @Mock
    private FragmentRepository fragmentRepository;

    @InjectMocks
    private FragmentService service;

    @Test
    @DisplayName("getByType retorna lista vacía si tipo inválido")
    void getByTypeInvalid() {
        var result = service.getByType("INVALIDO");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("update retorna null si id inexistente")
    void updateNotFound() {
        when(fragmentRepository.findById(999L)).thenReturn(Optional.empty());
        FragmentRequestDTO dto = FragmentRequestDTO.builder().type("CONTEXTO").text("t").build();
        assertNull(service.updateFromDTO(999L, dto));
    }

    @Test
    @DisplayName("delete retorna false si id inexistente")
    void deleteNotFound() {
        when(fragmentRepository.existsById(555L)).thenReturn(false);
        assertFalse(service.delete(555L));
    }

    @Test
    @DisplayName("getRandomFragment retorna null si no hay activos")
    void getRandomFragmentEmpty() {
        when(fragmentRepository.findByTypeAndActiveTrue(FragmentType.CONTEXTO)).thenReturn(List.of());
        assertNull(service.getRandomFragment(FragmentType.CONTEXTO));
    }
}
