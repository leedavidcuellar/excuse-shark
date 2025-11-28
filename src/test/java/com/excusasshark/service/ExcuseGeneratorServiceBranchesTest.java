package com.excusasshark.service;

import com.excusasshark.dto.ExcuseResponseDTO;
import com.excusasshark.dto.UltraSharkExcuseDTO;
import com.excusasshark.model.*;
import com.excusasshark.repository.ExcuseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("ExcuseGeneratorService Branch Coverage Tests")
class ExcuseGeneratorServiceBranchesTest {

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

    @Test
    @DisplayName("generateRandomExcuse retorna null si falta algún fragmento")
    void generateRandomExcuseMissingFragment() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(null);
        ExcuseResponseDTO result = service.generateRandomExcuse();
        assertNull(result);
    }

    @Test
    @DisplayName("generateExcuseWithMeme retorna excusa base si meme es null")
    void generateExcuseWithMemeMemeNull() {
        mockBasicFragments();
        Excuse saved = Excuse.builder().id(10L).contexto("c").causa("c").consecuencia("c").recomendacion("c").createdAt(LocalDateTime.now()).build();
        when(excuseRepository.save(any(Excuse.class))).thenReturn(saved);
        when(excuseRepository.findById(10L)).thenReturn(Optional.of(saved));
        when(memeService.getRandomMeme()).thenReturn(null);
        ExcuseResponseDTO result = service.generateExcuseWithMeme();
        assertNotNull(result);
        assertNull(result.getMeme());
    }

    @Test
    @DisplayName("generateExcuseWithMeme no agrega meme si entidad no encontrada")
    void generateExcuseWithMemeEntityNotFound() {
        mockBasicFragments();
        Excuse saved = Excuse.builder().id(11L).contexto("c").causa("c").consecuencia("c").recomendacion("c").createdAt(LocalDateTime.now()).build();
        when(excuseRepository.save(any(Excuse.class))).thenReturn(saved);
        when(excuseRepository.findById(11L)).thenReturn(Optional.empty());
        Meme meme = Meme.builder().id(1L).text("meme").build();
        when(memeService.getRandomMeme()).thenReturn(meme);
        ExcuseResponseDTO result = service.generateExcuseWithMeme();
        assertNotNull(result);
        assertNull(result.getMeme());
    }

    @Test
    @DisplayName("generateExcuseWithLaw retorna excusa base si ley es null")
    void generateExcuseWithLawLawNull() {
        mockBasicFragments();
        Excuse saved = Excuse.builder().id(12L).contexto("c").causa("c").consecuencia("c").recomendacion("c").createdAt(LocalDateTime.now()).build();
        when(excuseRepository.save(any(Excuse.class))).thenReturn(saved);
        when(excuseRepository.findById(12L)).thenReturn(Optional.of(saved));
        when(lawService.getRandomLaw()).thenReturn(null);
        ExcuseResponseDTO result = service.generateExcuseWithLaw();
        assertNotNull(result);
        assertNull(result.getLey());
    }

    @Test
    @DisplayName("generateExcuseWithLaw no agrega ley si entidad no encontrada")
    void generateExcuseWithLawEntityNotFound() {
        mockBasicFragments();
        Excuse saved = Excuse.builder().id(13L).contexto("c").causa("c").consecuencia("c").recomendacion("c").createdAt(LocalDateTime.now()).build();
        when(excuseRepository.save(any(Excuse.class))).thenReturn(saved);
        when(excuseRepository.findById(13L)).thenReturn(Optional.empty());
        Law law = Law.builder().id(1L).type(LawType.MURPHY).content("ley").build();
        when(lawService.getRandomLaw()).thenReturn(law);
        ExcuseResponseDTO result = service.generateExcuseWithLaw();
        assertNotNull(result);
        assertNull(result.getLey());
    }

    @Test
    @DisplayName("generateUltraSharkExcuse retorna null si excusa base null")
    void generateUltraSharkExcuseNull() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(null);
        UltraSharkExcuseDTO result = service.generateUltraSharkExcuse();
        assertNull(result);
    }

    @Test
    @DisplayName("generateUltraSharkExcuse usa camino alterno cuando ExcuseEntity es null y meme/ley null")
    void generateUltraSharkExcuseEntityNullAndNoExtras() {
        mockBasicFragments();
        Excuse saved = Excuse.builder().id(20L).contexto("c").causa("c").consecuencia("c").recomendacion("c").createdAt(LocalDateTime.now()).build();
        when(excuseRepository.save(any(Excuse.class))).thenReturn(saved);
        when(excuseRepository.findById(20L)).thenReturn(Optional.empty());
        when(memeService.getRandomMeme()).thenReturn(null);
        when(lawService.getRandomLaw()).thenReturn(null);
        UltraSharkExcuseDTO result = service.generateUltraSharkExcuse();
        assertNotNull(result);
        assertNotNull(result.getExcusa());
        assertNull(result.getMeme());
        assertNull(result.getLey());
    }

    @Test
    @DisplayName("generateExcuseForRole retorna excusa base sin role si entidad no encontrada")
    void generateExcuseForRoleEntityNull() {
        mockBasicFragments();
        Excuse saved = Excuse.builder().id(30L).contexto("c").causa("c").consecuencia("c").recomendacion("c").createdAt(LocalDateTime.now()).build();
        when(excuseRepository.save(any(Excuse.class))).thenReturn(saved);
        when(excuseRepository.findById(30L)).thenReturn(Optional.empty());
        ExcuseResponseDTO result = service.generateExcuseForRole(RoleType.DEVOPS);
        assertNotNull(result);
        assertNull(result.getRoleTarget());
    }

    @Test
    @DisplayName("getDailyExcuse retorna existente si ya hay del día")
    void getDailyExcuseExisting() {
        mockBasicFragments();
        Excuse existing = Excuse.builder().id(99L).contexto("ctx").causa("cs").consecuencia("cn").recomendacion("rm").createdAt(LocalDateTime.now()).build();
        when(excuseRepository.findByCreatedAtAfter(any())).thenReturn(List.of(existing));
        ExcuseResponseDTO result = service.getDailyExcuse();
        assertNotNull(result);
        assertEquals("ctx", result.getContexto());
    }

    private void mockBasicFragments() {
        when(fragmentService.getRandomFragment(FragmentType.CONTEXTO)).thenReturn(Fragment.builder().id(1L).type(FragmentType.CONTEXTO).text("contexto").build());
        when(fragmentService.getRandomFragment(FragmentType.CAUSA)).thenReturn(Fragment.builder().id(2L).type(FragmentType.CAUSA).text("causa").build());
        when(fragmentService.getRandomFragment(FragmentType.CONSECUENCIA)).thenReturn(Fragment.builder().id(3L).type(FragmentType.CONSECUENCIA).text("consecuencia").build());
        when(fragmentService.getRandomFragment(FragmentType.RECOMENDACION)).thenReturn(Fragment.builder().id(4L).type(FragmentType.RECOMENDACION).text("recomendacion").build());
    }
}
