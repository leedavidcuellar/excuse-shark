package com.excusasshark.service;

import com.excusasshark.dto.FragmentRequestDTO;
import com.excusasshark.dto.FragmentResponseDTO;
import com.excusasshark.model.Fragment;
import com.excusasshark.model.FragmentType;
import com.excusasshark.repository.FragmentRepository;
import com.excusasshark.service.mapper.FragmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de fragmentos
 */
@Service
@RequiredArgsConstructor
public class FragmentService {

    private final FragmentRepository fragmentRepository;

    public Fragment create(Fragment fragment) {
        return fragmentRepository.save(fragment);
    }

    public Fragment createFromDTO(FragmentRequestDTO dto) {
        Fragment fragment = FragmentMapper.toEntity(dto);
        return create(fragment);
    }

    public FragmentResponseDTO createFromDTOResponse(FragmentRequestDTO dto) {
        Fragment created = createFromDTO(dto);
        return FragmentMapper.toResponse(created);
    }

    public FragmentResponseDTO getById(Long id) {
        return fragmentRepository.findById(id)
                .map(FragmentMapper::toResponse)
                .orElse(null);
    }

    public List<FragmentResponseDTO> getAll() {
        return fragmentRepository.findAll()
                .stream()
                .map(FragmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<FragmentResponseDTO> getByType(String type) {
        try {
            FragmentType fragmentType = FragmentType.valueOf(type.toUpperCase());
            return fragmentRepository.findByTypeAndActiveTrue(fragmentType)
                    .stream()
                    .map(FragmentMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    public List<FragmentResponseDTO> getActive() {
        return fragmentRepository.findByActiveTrue()
                .stream()
                .map(FragmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Fragment update(Long id, FragmentRequestDTO dto) {
        return fragmentRepository.findById(id)
                .map(existing -> {
                    FragmentMapper.updateEntity(dto, existing);
                    return fragmentRepository.save(existing);
                })
                .orElse(null);
    }

    public FragmentResponseDTO updateFromDTO(Long id, FragmentRequestDTO dto) {
        Fragment updated = update(id, dto);
        return updated != null ? FragmentMapper.toResponse(updated) : null;
    }

    public boolean delete(Long id) {
        if (fragmentRepository.existsById(id)) {
            fragmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Fragment getRandomFragment(FragmentType type) {
        List<Fragment> fragments = fragmentRepository.findByTypeAndActiveTrue(type);
        if (fragments.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * fragments.size());
        return fragments.get(randomIndex);
    }
}
