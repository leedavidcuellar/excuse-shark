package com.excusasshark.service;

import com.excusasshark.model.Law;
import com.excusasshark.model.LawType;
import com.excusasshark.repository.LawRepository;
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
@DisplayName("LawService Branch Coverage Tests")
class LawServiceBranchesTest {

    @Mock
    private LawRepository lawRepository;

    @InjectMocks
    private LawService service;

    @Test
    @DisplayName("getActive retorna lista vacía si no hay activas")
    void getActiveEmpty() {
        when(lawRepository.findByActiveTrue()).thenReturn(List.of());
        var result = service.getActive();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("getByType retorna lista vacía si no hay del tipo")
    void getByTypeEmpty() {
        when(lawRepository.findByType(LawType.MURPHY)).thenReturn(List.of());
        var result = service.getByType(LawType.MURPHY);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
