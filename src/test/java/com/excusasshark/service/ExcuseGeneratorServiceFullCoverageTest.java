package com.excusasshark.service;

import com.excusasshark.dto.ExcuseResponseDTO;
import com.excusasshark.dto.UltraSharkExcuseDTO;
import com.excusasshark.model.*;
import com.excusasshark.repository.ExcuseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("ExcuseGeneratorService - 100% Coverage Tests")
class ExcuseGeneratorServiceFullCoverageTest {

    @Mock
    private FragmentService fragmentService;

    @Mock
    private MemeService memeService;

    @Mock
    private LawService lawService;

    @Mock
    private ExcuseRepository excuseRepository;

    @InjectMocks
    private ExcuseGeneratorService service;

    private Fragment contexto, causa, consecuencia, recomendacion;
    private Excuse excuse;
    private Meme meme;
    private Law law;

    @BeforeEach
    void setUp() {
        contexto = Fragment.builder().id(1L).type(FragmentType.CONTEXTO).text(CONTEXTO).build();
        causa = Fragment.builder().id(2L).type(FragmentType.CAUSA).text(CAUSA).build();
        consecuencia = Fragment.builder().id(3L).type(FragmentType.CONSECUENCIA).text(CONSECUENCIA).build();
        recomendacion = Fragment.builder().id(4L).type(FragmentType.RECOMENDACION).text(RECOMENDACION).build();

        excuse = Excuse.builder()
                .id(1L)
                .contexto(CONTEXTO)
                .causa(CAUSA)
                .consecuencia(CONSECUENCIA)
                .recomendacion(RECOMENDACION)
                .build();

        meme = Meme.builder().id(1L).text(TEST_MEME_TEST).build();
        law = Law.builder().id(1L).type(LawType.MURPHY).content(TEST_LEY_TEST).build();
    }

    @Test
    @DisplayName("generateRandomExcuse cuando contexto es null retorna null")
    void generateRandomExcuse_WhenContextoNull_ReturnsNull() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(null);
        when(fragmentService.getRandomFragment(FragmentType.CAUSA)).thenReturn(causa);
        when(fragmentService.getRandomFragment(FragmentType.CONSECUENCIA)).thenReturn(consecuencia);
        when(fragmentService.getRandomFragment(FragmentType.RECOMENDACION)).thenReturn(recomendacion);

        ExcuseResponseDTO result = service.generateRandomExcuse();

        assertNull(result);
        verify(excuseRepository, never()).save(any());
    }

    @Test
    @DisplayName("generateRandomExcuse cuando causa es null retorna null")
    void generateRandomExcuse_WhenCausaNull_ReturnsNull() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(contexto);
        when(fragmentService.getRandomFragment(FragmentType.CAUSA)).thenReturn(null);
        when(fragmentService.getRandomFragment(FragmentType.CONSECUENCIA)).thenReturn(consecuencia);
        when(fragmentService.getRandomFragment(FragmentType.RECOMENDACION)).thenReturn(recomendacion);

        ExcuseResponseDTO result = service.generateRandomExcuse();

        assertNull(result);
        verify(excuseRepository, never()).save(any());
    }

    @Test
    @DisplayName("generateRandomExcuse cuando consecuencia es null retorna null")
    void generateRandomExcuse_WhenConsecuenciaNull_ReturnsNull() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(contexto);
        when(fragmentService.getRandomFragment(FragmentType.CAUSA)).thenReturn(causa);
        when(fragmentService.getRandomFragment(FragmentType.CONSECUENCIA)).thenReturn(null);
        when(fragmentService.getRandomFragment(FragmentType.RECOMENDACION)).thenReturn(recomendacion);

        ExcuseResponseDTO result = service.generateRandomExcuse();

        assertNull(result);
        verify(excuseRepository, never()).save(any());
    }

    @Test
    @DisplayName("generateRandomExcuse cuando recomendacion es null retorna null")
    void generateRandomExcuse_WhenRecomendacionNull_ReturnsNull() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(contexto);
        when(fragmentService.getRandomFragment(FragmentType.CAUSA)).thenReturn(causa);
        when(fragmentService.getRandomFragment(FragmentType.CONSECUENCIA)).thenReturn(consecuencia);
        when(fragmentService.getRandomFragment(FragmentType.RECOMENDACION)).thenReturn(null);

        ExcuseResponseDTO result = service.generateRandomExcuse();

        assertNull(result);
        verify(excuseRepository, never()).save(any());
    }

    @Test
    @DisplayName("generateExcuseWithMeme cuando generateRandomExcuse retorna null")
    void generateExcuseWithMemeWhenRandomExcuseNullReturnsNull() {
        when(fragmentService.getRandomFragment(any())).thenReturn(null);

        ExcuseResponseDTO result = service.generateExcuseWithMeme();

        assertNull(result);
        verify(memeService, never()).getRandomMeme();
    }

    @Test
    @DisplayName("generateExcuseWithLaw cuando generateRandomExcuse retorna null")
    void generateExcuseWithLaw_WhenRandomExcuseNull_ReturnsNull() {
        when(fragmentService.getRandomFragment(any())).thenReturn(null);

        ExcuseResponseDTO result = service.generateExcuseWithLaw();

        assertNull(result);
        verify(lawService, never()).getRandomLaw();
    }

    @Test
    @DisplayName("generateExcuseForRole cuando generateRandomExcuse retorna null")
    void generateExcuseForRole_WhenRandomExcuseNull_ReturnsNull() {
        when(fragmentService.getRandomFragment(any())).thenReturn(null);

        ExcuseResponseDTO result = service.generateExcuseForRole(RoleType.DEV);

        assertNull(result);
        verify(excuseRepository, never()).findById(any());
    }

    @Test
    @DisplayName("generateUltraSharkExcuse cuando meme es null")
    void generateUltraSharkExcuse_WhenMemeNull_StillWorks() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(contexto);
        when(fragmentService.getRandomFragment(FragmentType.CAUSA)).thenReturn(causa);
        when(fragmentService.getRandomFragment(FragmentType.CONSECUENCIA)).thenReturn(consecuencia);
        when(fragmentService.getRandomFragment(FragmentType.RECOMENDACION)).thenReturn(recomendacion);
        when(excuseRepository.save(any(Excuse.class))).thenReturn(excuse);
        when(excuseRepository.findById(1L)).thenReturn(Optional.of(excuse));
        when(memeService.getRandomMeme()).thenReturn(null);
        when(lawService.getRandomLaw()).thenReturn(law);

        UltraSharkExcuseDTO result = service.generateUltraSharkExcuse();

        assertNotNull(result);
        assertNull(result.getExcusa().getMeme());
        assertNotNull(result.getLey());
        verify(memeService).getRandomMeme();
        verify(lawService).getRandomLaw();
    }

    @Test
    @DisplayName("generateUltraSharkExcuse cuando law es null")
    void generateUltraSharkExcuse_WhenLawNull_StillWorks() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(contexto);
        when(fragmentService.getRandomFragment(FragmentType.CAUSA)).thenReturn(causa);
        when(fragmentService.getRandomFragment(FragmentType.CONSECUENCIA)).thenReturn(consecuencia);
        when(fragmentService.getRandomFragment(FragmentType.RECOMENDACION)).thenReturn(recomendacion);
        when(excuseRepository.save(any(Excuse.class))).thenReturn(excuse);
        when(excuseRepository.findById(1L)).thenReturn(Optional.of(excuse));
        when(memeService.getRandomMeme()).thenReturn(meme);
        when(lawService.getRandomLaw()).thenReturn(null);

        UltraSharkExcuseDTO result = service.generateUltraSharkExcuse();

        assertNotNull(result);
        assertNotNull(result.getMeme());
        assertNull(result.getExcusa().getLey());
        verify(memeService).getRandomMeme();
        verify(lawService).getRandomLaw();
    }

    @Test
    @DisplayName("generateUltraSharkExcuse cuando ambos meme y law son null en fallback")
    void generateUltraSharkExcuse_WhenBothNull_InFallbackPath() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(contexto);
        when(fragmentService.getRandomFragment(FragmentType.CAUSA)).thenReturn(causa);
        when(fragmentService.getRandomFragment(FragmentType.CONSECUENCIA)).thenReturn(consecuencia);
        when(fragmentService.getRandomFragment(FragmentType.RECOMENDACION)).thenReturn(recomendacion);
        when(excuseRepository.save(any(Excuse.class))).thenReturn(excuse);
        when(excuseRepository.findById(1L)).thenReturn(Optional.empty()); // Forzar path de fallback
        when(memeService.getRandomMeme()).thenReturn(null);
        when(lawService.getRandomLaw()).thenReturn(null);

        UltraSharkExcuseDTO result = service.generateUltraSharkExcuse();

        assertNotNull(result);
        assertNull(result.getMeme());
        assertNull(result.getLey());
    }

    @Test
    @DisplayName("generateUltraSharkExcuse fallback con meme null y law NOT null - ternario línea 120")
    void generateUltraSharkExcuse_FallbackPath_MemeNullLawNotNull() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(contexto);
        when(fragmentService.getRandomFragment(FragmentType.CAUSA)).thenReturn(causa);
        when(fragmentService.getRandomFragment(FragmentType.CONSECUENCIA)).thenReturn(consecuencia);
        when(fragmentService.getRandomFragment(FragmentType.RECOMENDACION)).thenReturn(recomendacion);
        when(excuseRepository.save(any(Excuse.class))).thenReturn(excuse);
        when(excuseRepository.findById(1L)).thenReturn(Optional.empty());
        when(memeService.getRandomMeme()).thenReturn(null); // null
        when(lawService.getRandomLaw()).thenReturn(law); // NOT null

        UltraSharkExcuseDTO result = service.generateUltraSharkExcuse();

        assertNotNull(result);
        assertNull(result.getMeme()); // línea 120 evalúa a null
        assertNotNull(result.getLey()); // línea 121 evalúa el valor
    }

    @Test
    @DisplayName("generateUltraSharkExcuse fallback con meme NOT null y law null - ternario línea 121")
    void generateUltraSharkExcuse_FallbackPath_MemeNotNullLawNull() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(contexto);
        when(fragmentService.getRandomFragment(FragmentType.CAUSA)).thenReturn(causa);
        when(fragmentService.getRandomFragment(FragmentType.CONSECUENCIA)).thenReturn(consecuencia);
        when(fragmentService.getRandomFragment(FragmentType.RECOMENDACION)).thenReturn(recomendacion);
        when(excuseRepository.save(any(Excuse.class))).thenReturn(excuse);
        when(excuseRepository.findById(1L)).thenReturn(Optional.empty());
        when(memeService.getRandomMeme()).thenReturn(meme); // NOT null
        when(lawService.getRandomLaw()).thenReturn(null); // null

        UltraSharkExcuseDTO result = service.generateUltraSharkExcuse();

        assertNotNull(result);
        assertNotNull(result.getMeme()); // línea 120 evalúa el valor
        assertNull(result.getLey()); // línea 121 evalúa a null
    }
}
