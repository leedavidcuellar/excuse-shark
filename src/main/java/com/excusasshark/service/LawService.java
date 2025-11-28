package com.excusasshark.service;

import com.excusasshark.dto.LawResponseDTO;
import com.excusasshark.model.Law;
import com.excusasshark.model.LawType;
import com.excusasshark.repository.LawRepository;
import com.excusasshark.service.mapper.LawMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de leyes y axiomas
 */
@Service
@RequiredArgsConstructor
public class LawService {

    private final LawRepository lawRepository;
    private final Random random = new Random();

    public Law create(Law law) {
        return lawRepository.save(law);
    }

    public LawResponseDTO getById(Long id) {
        return lawRepository.findById(id)
                .map(LawMapper::toResponse)
                .orElse(null);
    }

    public List<LawResponseDTO> getAll() {
        return lawRepository.findAll()
                .stream()
                .map(LawMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<LawResponseDTO> getActive() {
        return lawRepository.findByActiveTrue()
                .stream()
                .map(LawMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<LawResponseDTO> getByType(LawType type) {
        return lawRepository.findByType(type)
                .stream()
                .map(LawMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Law getRandomLaw() {
        List<Law> laws = lawRepository.findByActiveTrue();
        if (laws.isEmpty()) {
            return null;
        }
        int randomIndex = random.nextInt(laws.size());
        return laws.get(randomIndex);
    }

    public boolean delete(Long id) {
        if (lawRepository.existsById(id)) {
            lawRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
