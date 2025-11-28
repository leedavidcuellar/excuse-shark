package com.excusasshark.service;

import com.excusasshark.model.Meme;
import com.excusasshark.repository.MemeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemeService Branch Coverage Tests")
class MemeServiceBranchesTest {

    @Mock
    private MemeRepository memeRepository;

    @InjectMocks
    private MemeService service;

    @Test
    @DisplayName("getActive retorna lista vacía si no hay activos")
    void getActiveEmpty() {
        when(memeRepository.findByActiveTrue()).thenReturn(List.of());
        var result = service.getActive();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
