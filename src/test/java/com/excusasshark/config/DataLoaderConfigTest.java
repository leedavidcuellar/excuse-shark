package com.excusasshark.config;

import com.excusasshark.service.FragmentService;
import com.excusasshark.service.LawService;
import com.excusasshark.service.MemeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DataLoaderConfig Tests")
class DataLoaderConfigTest {

    @Mock
    private FragmentService fragmentService;
    @Mock
    private MemeService memeService;
    @Mock
    private LawService lawService;

    @InjectMocks
    private DataLoaderConfig dataLoaderConfig;

    @Test
    @DisplayName("loadInitialData carga datos desde JSON si existen, o fallback")
    void loadInitialDataInvocations() throws Exception {
        CommandLineRunner runner = dataLoaderConfig.loadInitialData(fragmentService, memeService, lawService);
        assertNotNull(runner);
        runner.run();
        // Si hay JSON en docs/json, habrá invocaciones según contenido; si no, se usa fallback.
        // Validamos que al menos se haya intentado crear alguna entidad sin fallar.
        verify(fragmentService, atLeast(1)).create(any());
        verify(memeService, atLeast(1)).create(any());
        verify(lawService, atLeast(1)).create(any());
    }
}
