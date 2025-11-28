package com.excusasshark.service;

import com.excusasshark.dto.ExcuseResponseDTO;
import com.excusasshark.dto.UltraSharkExcuseDTO;
import com.excusasshark.model.*;
import com.excusasshark.repository.ExcuseRepository;
import com.excusasshark.service.mapper.ExcuseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Servicio generador de excusas tech - CORE del negocio
 */
@Service
@RequiredArgsConstructor
public class ExcuseGeneratorService {

    private final FragmentService fragmentService;
    private final MemeService memeService;
    private final LawService lawService;
    private final ExcuseRepository excuseRepository;
    private final Random random = new Random();

    /**
     * Genera una excusa aleatoria simple (contexto + causa + consecuencia + recomendación)
     */
    public ExcuseResponseDTO generateRandomExcuse() {
        Fragment contexto = fragmentService.getRandomFragment(FragmentType.CONTEXTO);
        Fragment causa = fragmentService.getRandomFragment(FragmentType.CAUSA);
        Fragment consecuencia = fragmentService.getRandomFragment(FragmentType.CONSECUENCIA);
        Fragment recomendacion = fragmentService.getRandomFragment(FragmentType.RECOMENDACION);

        if (contexto == null || causa == null || consecuencia == null || recomendacion == null) {
            return null;
        }

        Excuse excuse = Excuse.builder()
                .contexto(contexto.getText())
                .causa(causa.getText())
                .consecuencia(consecuencia.getText())
                .recomendacion(recomendacion.getText())
                .build();

        Excuse saved = excuseRepository.save(excuse);
        return ExcuseMapper.toResponse(saved);
    }

    /**
     * Genera excusa con meme
     */
    public ExcuseResponseDTO generateExcuseWithMeme() {
        ExcuseResponseDTO excuse = generateRandomExcuse();
        if (excuse == null) {
            return null;
        }

        Excuse excuseEntity = excuseRepository.findById(excuse.getId()).orElse(null);
        if (excuseEntity == null) {
            return excuse;
        }

        Meme meme = memeService.getRandomMeme();
        if (meme != null) {
            excuseEntity.setMeme(meme.getText());
        }

        Excuse saved = excuseRepository.save(excuseEntity);
        return ExcuseMapper.toResponse(saved);
    }

    /**
     * Genera excusa con ley
     */
    public ExcuseResponseDTO generateExcuseWithLaw() {
        ExcuseResponseDTO excuse = generateRandomExcuse();
        if (excuse == null) {
            return null;
        }

        Excuse excuseEntity = excuseRepository.findById(excuse.getId()).orElse(null);
        if (excuseEntity == null) {
            return excuse;
        }

        Law law = lawService.getRandomLaw();
        if (law != null) {
            excuseEntity.setLey(law.getType() + " - " + law.getContent());
        }

        Excuse saved = excuseRepository.save(excuseEntity);
        return ExcuseMapper.toResponse(saved);
    }

    /**
     * Genera excusa en modo ULTRA SHARK: excusa + meme + ley
     */
    public UltraSharkExcuseDTO generateUltraSharkExcuse() {
        ExcuseResponseDTO excuse = generateRandomExcuse();
        if (excuse == null) {
            return null;
        }

        Meme meme = memeService.getRandomMeme();
        Law law = lawService.getRandomLaw();

        Excuse excuseEntity = excuseRepository.findById(excuse.getId()).orElse(null);
        if (excuseEntity != null) {
            if (meme != null) {
                excuseEntity.setMeme(meme.getText());
            }
            if (law != null) {
                excuseEntity.setLey(law.getType() + " - " + law.getContent());
            }
            excuseRepository.save(excuseEntity);
            excuse = ExcuseMapper.toResponse(excuseEntity);
        }

        return UltraSharkExcuseDTO.builder()
                .excusa(excuse)
                .meme(meme != null ? meme.getText() : null)
                .ley(law != null ? (law.getType() + " - " + law.getContent()) : null)
                .build();
    }

    /**
     * Genera excusa para un rol específico (dev, qa, devops, pm, etc)
     */
    public ExcuseResponseDTO generateExcuseForRole(RoleType role) {
        ExcuseResponseDTO excuse = generateRandomExcuse();
        if (excuse != null) {
            Excuse excuseEntity = excuseRepository.findById(excuse.getId()).orElse(null);
            if (excuseEntity != null) {
                excuseEntity.setRoleTarget(role);
                Excuse saved = excuseRepository.save(excuseEntity);
                return ExcuseMapper.toResponse(saved);
            }
        }
        return excuse;
    }

    /**
     * Obtiene la excusa del día (generada una vez al día)
     */
    public ExcuseResponseDTO getDailyExcuse() {
        LocalDateTime today = LocalDateTime.now().minusHours(LocalDateTime.now().getHour() + LocalDateTime.now().getMinute() / 60L);
        List<Excuse> todayExcuses = excuseRepository.findByCreatedAtAfter(today);
        
        if (!todayExcuses.isEmpty()) {
            // Ya existe una del día, retornarla
            return ExcuseMapper.toResponse(todayExcuses.get(0));
        }
        
        // Generar nueva
        return generateUltraSharkExcuse().getExcusa();
    }

    /**
     * Genera reproducible con seed (para tests)
     */
    public ExcuseResponseDTO generateExcuseWithSeed(long seed) {
        random.setSeed(seed);
        
        // Simplificado: aquí se usaría random con seed
                return generateRandomExcuse();
    }

    public List<ExcuseResponseDTO> getAll() {
        return excuseRepository.findByActiveTrue()
                .stream()
                .map(ExcuseMapper::toResponse)
                .toList();
    }

    public ExcuseResponseDTO getById(Long id) {
        return excuseRepository.findById(id)
                .map(ExcuseMapper::toResponse)
                .orElse(null);
    }
}
