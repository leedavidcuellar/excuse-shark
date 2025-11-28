package com.excusasshark.service;

import com.excusasshark.dto.ExcuseResponseDTO;
import com.excusasshark.dto.FragmentResponseDTO;
import com.excusasshark.dto.UltraSharkExcuseDTO;
import com.excusasshark.model.Excuse;
import com.excusasshark.model.Fragment;
import com.excusasshark.model.FragmentType;
import com.excusasshark.model.Law;
import com.excusasshark.model.LawType;
import com.excusasshark.model.Meme;
import com.excusasshark.model.RoleType;
import com.excusasshark.repository.ExcuseRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static com.excusasshark.constants.TestConstants.*;

/**
 * Tests unitarios para ExcuseGeneratorService
 * Verifica toda la lógica de generación de excusas
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ExcuseGeneratorService Tests")
class ExcuseGeneratorServiceTest {

    @Mock
    private ExcuseRepository excuseRepository;

    @Mock
    private FragmentService fragmentService;

    @Mock
    private MemeService memeService;

    @Mock
    private LawService lawService;

    @InjectMocks
    private ExcuseGeneratorService excuseGeneratorService;

    @BeforeEach
    void setUp() {
        // Configurar mocks mínimos para que los tests unitarios no retornen null
        Fragment contexto = Fragment.builder().id(1L).type(FragmentType.CONTEXTO).text(TEXT_CONTEXTO).build();
        Fragment causa = Fragment.builder().id(2L).type(FragmentType.CAUSA).text(TEXT_CAUSA).build();
        Fragment consecuencia = Fragment.builder().id(3L).type(FragmentType.CONSECUENCIA).text(TEXT_CONSECUENCIA).build();
        Fragment recomendacion = Fragment.builder().id(4L).type(FragmentType.RECOMENDACION).text(TEXT_RECOMENDACION).build();

        lenient().when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(contexto);
        lenient().when(fragmentService.getRandomFragment(FragmentType.CAUSA)).thenReturn(causa);
        lenient().when(fragmentService.getRandomFragment(FragmentType.CONSECUENCIA)).thenReturn(consecuencia);
        lenient().when(fragmentService.getRandomFragment(FragmentType.RECOMENDACION)).thenReturn(recomendacion);

        lenient().when(fragmentService.getActive()).thenReturn(List.of(
            FragmentResponseDTO.builder().id(1L).type(FragmentType.CONTEXTO).text(TEXT_CONTEXTO).build(),
            FragmentResponseDTO.builder().id(2L).type(FragmentType.CAUSA).text(TEXT_CAUSA).build(),
            FragmentResponseDTO.builder().id(3L).type(FragmentType.CONSECUENCIA).text(TEXT_CONSECUENCIA).build(),
            FragmentResponseDTO.builder().id(4L).type(FragmentType.RECOMENDACION).text(TEXT_RECOMENDACION).build()
        ));

        Meme meme = Meme.builder().id(1L).text(TEXT_MEME).build();
        lenient().when(memeService.getRandomMeme()).thenReturn(meme);

        Law law = Law.builder().id(1L).type(LawType.MURPHY).content(TEXT_LAW_CONTENT).build();
        lenient().when(lawService.getRandomLaw()).thenReturn(law);

        Excuse saved = Excuse.builder()
            .id(1L)
            .contexto(contexto.getText())
            .causa(causa.getText())
            .consecuencia(consecuencia.getText())
            .recomendacion(recomendacion.getText())
            .createdAt(LocalDateTime.now())
            .build();

        lenient().when(excuseRepository.save(any(Excuse.class))).thenReturn(saved);
        lenient().when(excuseRepository.findById(anyLong())).thenReturn(Optional.of(saved));
        lenient().when(excuseRepository.findByCreatedAtAfter(any())).thenReturn(List.of());
        lenient().when(excuseRepository.findByActiveTrue()).thenReturn(List.of());
    }

    @Test
    @DisplayName("Debe generar una excusa aleatoria con 4 fragmentos")
    void testGenerateRandomExcuse() {
        // Arrange
        ExcuseResponseDTO excuse = excuseGeneratorService.generateRandomExcuse();

        // Assert
        assertNotNull(excuse, MSG_EXCUSE_NOT_NULL);
        assertNotNull(excuse.getContexto(), MSG_CONTEXT_PRESENT);
        assertNotNull(excuse.getCausa(), MSG_CAUSE_PRESENT);
        assertNotNull(excuse.getConsecuencia(), MSG_CONSEQUENCE_PRESENT);
        assertNotNull(excuse.getRecomendacion(), MSG_RECOMMENDATION_PRESENT);
    }

    @Test
    @DisplayName("Debe generar excusa con meme")
    void testGenerateExcuseWithMeme() {
        // Arrange
        ExcuseResponseDTO excuse = excuseGeneratorService.generateExcuseWithMeme();

        // Assert
        assertNotNull(excuse, MSG_EXCUSE_WITH_MEME_NOT_NULL);
        assertNotNull(excuse.getContexto(), MSG_CONTEXT_PRESENT);
        assertNotNull(excuse.getMeme(), MSG_MEME_PRESENT);
    }

    @Test
    @DisplayName("Debe generar excusa con ley técnica")
    void testGenerateExcuseWithLaw() {
        // Arrange
        ExcuseResponseDTO excuse = excuseGeneratorService.generateExcuseWithLaw();

        // Assert
        assertNotNull(excuse, MSG_EXCUSE_WITH_LAW_NOT_NULL);
        assertNotNull(excuse.getContexto(), MSG_CONTEXT_PRESENT);
        assertNotNull(excuse.getLey(), MSG_LAW_PRESENT);
    }

    @Test
    @DisplayName("Debe generar UltraShark (excusa + meme + ley)")
    void testGenerateUltraSharkExcuse() {
        // Arrange
        UltraSharkExcuseDTO ultraShark = excuseGeneratorService.generateUltraSharkExcuse();

        // Assert
        assertNotNull(ultraShark, MSG_ULTRASHARK_NOT_NULL);
        assertNotNull(ultraShark.getExcusa(), MSG_EXCUSE_PRESENT);
        assertNotNull(ultraShark.getMeme(), MSG_MEME_PRESENT);
        assertNotNull(ultraShark.getLey(), MSG_LAW_PRESENT);
    }

    @Test
    @DisplayName("Debe generar excusa para rol específico (DEV)")
    void testGenerateExcuseForRoleDev() {
        // Arrange
        ExcuseResponseDTO excuse = excuseGeneratorService.generateExcuseForRole(RoleType.DEV);

        // Assert
        assertNotNull(excuse, "La excusa para DEV no debe ser nula");
        assertEquals(RoleType.DEV, excuse.getRoleTarget(), "El roleTarget debe ser DEV");
    }

    @Test
    @DisplayName("Debe generar excusa para rol QA")
    void testGenerateExcuseForRoleQA() {
        // Arrange
        ExcuseResponseDTO excuse = excuseGeneratorService.generateExcuseForRole(RoleType.QA);

        // Assert
        assertNotNull(excuse, "La excusa para QA no debe ser nula");
        assertEquals(RoleType.QA, excuse.getRoleTarget(), "El roleTarget debe ser QA");
    }

    @Test
    @DisplayName("Debe retornar la misma excusa en el mismo día (getDailyExcuse)")
    void testGetDailyExcuseSameDay() {
        // Arrange
        ExcuseResponseDTO excuse1 = excuseGeneratorService.getDailyExcuse();
        ExcuseResponseDTO excuse2 = excuseGeneratorService.getDailyExcuse();

        // Assert
        assertNotNull(excuse1, "La excusa del día no debe ser nula");
        assertNotNull(excuse2, "La segunda excusa del día no debe ser nula");
        assertEquals(excuse1.getContexto(), excuse2.getContexto(), 
                "Dentro del mismo día, el contexto debe ser el mismo");
    }

    @Test
    @DisplayName("Debe generar excusa reproducible con seed")
    void testGenerateExcuseWithSeed() {
        // Arrange
        long seed = 12345L;

        // Act
        ExcuseResponseDTO excuse1 = excuseGeneratorService.generateExcuseWithSeed(seed);
        ExcuseResponseDTO excuse2 = excuseGeneratorService.generateExcuseWithSeed(seed);

        // Assert
        assertNotNull(excuse1, "Primera excusa con seed no debe ser nula");
        assertNotNull(excuse2, "Segunda excusa con seed no debe ser nula");
        assertEquals(excuse1.getContexto(), excuse2.getContexto(), 
                "Misma seed debe producir mismo contexto");
    }

    @Test
    @DisplayName("Debe retornar lista de todas las excusas")
    void testGetAllExcuses() {
        // Act
        var excuses = excuseGeneratorService.getAll();

        // Assert
        assertNotNull(excuses, "La lista de excusas no debe ser nula");
    }

    @Test
    @DisplayName("Debe retornar excusa por ID")
    void testGetExcuseById() {
        // Arrange
        Long excuseId = 1L;

        // Act
        ExcuseResponseDTO result = excuseGeneratorService.getById(excuseId);

        // Assert
        assertNotNull(result, "El resultado no debe ser nulo");
        assertNotNull(result.getContexto(), "El contexto debe estar presente");
        assertNotNull(result.getCausa(), "La causa debe estar presente");
        assertNotNull(result.getConsecuencia(), "La consecuencia debe estar presente");
        assertNotNull(result.getRecomendacion(), "La recomendación debe estar presente");
    }
}
