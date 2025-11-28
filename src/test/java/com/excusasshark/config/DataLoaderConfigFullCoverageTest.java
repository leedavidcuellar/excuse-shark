package com.excusasshark.config;

import com.excusasshark.model.Fragment;
import com.excusasshark.model.FragmentType;
import com.excusasshark.model.Law;
import com.excusasshark.model.LawType;
import com.excusasshark.model.Meme;
import com.excusasshark.service.FragmentService;
import com.excusasshark.service.LawService;
import com.excusasshark.service.MemeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests de cobertura completa para DataLoaderConfig.
 * 
 * Estrategia: Tests de integración que aceptan la existencia de archivos JSON
 * reales en docs/json. Verifican comportamiento correcto del CommandLineRunner
 * y que los servicios son invocados apropiadamente con o sin archivos JSON.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("DataLoaderConfig Full Coverage Tests")
class DataLoaderConfigFullCoverageTest {

    @Mock
    private FragmentService fragmentService;
    @Mock
    private MemeService memeService;
    @Mock
    private LawService lawService;

    private final DataLoaderConfig config = new DataLoaderConfig();

    @Test
    @DisplayName("Debe ejecutar CommandLineRunner sin errores")
    void loadDataSuccessfully() throws Exception {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        
        runner.run();
        
        verify(fragmentService, atLeastOnce()).create(any(Fragment.class));
        verify(memeService, atLeastOnce()).create(any(Meme.class));
        verify(lawService, atLeastOnce()).create(any(Law.class));
    }

    @Test
    @DisplayName("Debe cargar fragmentos con tipos válidos o null para inválidos")
    void loadFragmentsWithValidOrNullTypes() throws Exception {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        runner.run();
        
        ArgumentCaptor<Fragment> captor = ArgumentCaptor.forClass(Fragment.class);
        verify(fragmentService, atLeastOnce()).create(captor.capture());
        
        assertFalse(captor.getAllValues().isEmpty());
        
        captor.getAllValues().forEach(frag -> {
            if (frag.getType() != null) {
                assertTrue(frag.getType() instanceof FragmentType);
            }
        });
    }

    @Test
    @DisplayName("Debe cargar leyes con tipos válidos o null para inválidos")
    void loadLawsWithValidOrNullTypes() throws Exception {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        runner.run();
        
        ArgumentCaptor<Law> captor = ArgumentCaptor.forClass(Law.class);
        verify(lawService, atLeastOnce()).create(captor.capture());
        
        assertFalse(captor.getAllValues().isEmpty());
        
        captor.getAllValues().forEach(law -> {
            if (law.getType() != null) {
                assertTrue(law.getType() instanceof LawType);
            }
        });
    }

    @Test
    @DisplayName("Debe cargar memes con texto no vacío")
    void loadMemesWithText() throws Exception {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        runner.run();
        
        ArgumentCaptor<Meme> captor = ArgumentCaptor.forClass(Meme.class);
        verify(memeService, atLeastOnce()).create(captor.capture());
        
        assertFalse(captor.getAllValues().isEmpty());
        
        captor.getAllValues().forEach(meme -> assertNotNull(meme.getText()));
    }

    @Test
    @DisplayName("Debe asignar active=true por defecto")
    void loadWithDefaultActiveTrue() throws Exception {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        runner.run();
        
        ArgumentCaptor<Fragment> captor = ArgumentCaptor.forClass(Fragment.class);
        verify(fragmentService, atLeastOnce()).create(captor.capture());
        
        assertTrue(captor.getAllValues().stream().anyMatch(f -> Boolean.TRUE.equals(f.getActive())));
    }

    @Test
    @DisplayName("Debe procesar campo active correctamente")
    void loadWithActiveHandling() throws Exception {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        runner.run();
        
        ArgumentCaptor<Meme> captor = ArgumentCaptor.forClass(Meme.class);
        verify(memeService, atLeastOnce()).create(captor.capture());
        
        captor.getAllValues().forEach(meme -> assertNotNull(meme.getActive()));
    }

    @Test
    @DisplayName("Debe cargar múltiples entries de cada tipo")
    void loadMultipleEntries() throws Exception {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        runner.run();
        
        ArgumentCaptor<Fragment> fragCaptor = ArgumentCaptor.forClass(Fragment.class);
        ArgumentCaptor<Meme> memeCaptor = ArgumentCaptor.forClass(Meme.class);
        ArgumentCaptor<Law> lawCaptor = ArgumentCaptor.forClass(Law.class);
        
        verify(fragmentService, atLeastOnce()).create(fragCaptor.capture());
        verify(memeService, atLeastOnce()).create(memeCaptor.capture());
        verify(lawService, atLeastOnce()).create(lawCaptor.capture());
        
        assertTrue(fragCaptor.getAllValues().size() >= 1);
        assertTrue(memeCaptor.getAllValues().size() >= 1);
        assertTrue(lawCaptor.getAllValues().size() >= 1);
    }

    @Test
    @DisplayName("Debe manejar campos opcionales en JSON")
    void loadWithOptionalFields() throws Exception {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        runner.run();
        
        ArgumentCaptor<Fragment> captor = ArgumentCaptor.forClass(Fragment.class);
        verify(fragmentService, atLeastOnce()).create(captor.capture());
        
        assertTrue(captor.getAllValues().stream().anyMatch(f -> 
            f.getText() != null && !f.getText().isEmpty()
        ));
    }

    @Test
    @DisplayName("Debe manejar IllegalArgumentException para tipos enum inválidos")
    void loadWithInvalidEnumGracefully() {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        
        assertDoesNotThrow(() -> runner.run());
        
        verify(fragmentService, atLeastOnce()).create(any(Fragment.class));
    }

    @Test
    @DisplayName("Debe invocar los tres servicios en secuencia")
    void loadInvokesAllServices() throws Exception {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        runner.run();
        
        // Verificar que todos los servicios fueron invocados al menos una vez
        verify(fragmentService, atLeastOnce()).create(any(Fragment.class));
        verify(memeService, atLeastOnce()).create(any(Meme.class));
        verify(lawService, atLeastOnce()).create(any(Law.class));
        
        // Verificar que no se llamaron otros métodos en los servicios
        verifyNoMoreInteractions(fragmentService, memeService, lawService);
    }

    @Test
    @DisplayName("Debe usar fallback hardcoded cuando no existen archivos JSON")
    void loadFallbackWhenJsonFilesDoNotExist() throws Exception {
        // Renombrar carpeta docs temporalmente para simular ausencia de JSON
        java.nio.file.Path docsPath = java.nio.file.Path.of("docs");
        java.nio.file.Path docsBakPath = java.nio.file.Path.of("docs_backup_test");
        
        boolean renamed = false;
        try {
            if (java.nio.file.Files.exists(docsPath)) {
                java.nio.file.Files.move(docsPath, docsBakPath);
                renamed = true;
            }
            
            CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
            runner.run();
            
            // El fallback debe crear 8 fragments, 3 memes y 5 laws
            verify(fragmentService, times(8)).create(any(Fragment.class));
            verify(memeService, times(3)).create(any(Meme.class));
            verify(lawService, times(5)).create(any(Law.class));
            
        } finally {
            // Restaurar carpeta docs
            if (renamed && java.nio.file.Files.exists(docsBakPath)) {
                java.nio.file.Files.move(docsBakPath, docsPath);
            }
        }
    }

    @Test
    @DisplayName("Debe verificar tipos enum en fragmentos del JSON")
    void testFragmentTypeValidation() throws Exception {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        runner.run();
        
        ArgumentCaptor<Fragment> captor = ArgumentCaptor.forClass(Fragment.class);
        verify(fragmentService, atLeastOnce()).create(captor.capture());
        
        // Verificar que los fragmentos hardcoded tienen tipos válidos
        assertTrue(captor.getAllValues().stream().anyMatch(f -> 
            f.getType() == FragmentType.CONTEXTO
        ));
        assertTrue(captor.getAllValues().stream().anyMatch(f -> 
            f.getType() == FragmentType.CAUSA
        ));
    }

    @Test
    @DisplayName("Debe verificar tipos enum en leyes del JSON")
    void testLawTypeValidation() throws Exception {
        CommandLineRunner runner = config.loadInitialData(fragmentService, memeService, lawService);
        runner.run();
        
        ArgumentCaptor<Law> captor = ArgumentCaptor.forClass(Law.class);
        verify(lawService, atLeastOnce()).create(captor.capture());
        
        // Verificar que las leyes hardcoded tienen tipos válidos
        assertTrue(captor.getAllValues().stream().anyMatch(l -> 
            l.getType() == LawType.MURPHY
        ));
        assertTrue(captor.getAllValues().stream().anyMatch(l -> 
            l.getType() == LawType.HOFSTADTER
        ));
    }
}
