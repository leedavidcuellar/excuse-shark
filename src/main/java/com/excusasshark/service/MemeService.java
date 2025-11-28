package com.excusasshark.service;

import com.excusasshark.dto.MemeResponseDTO;
import com.excusasshark.model.Meme;
import com.excusasshark.repository.MemeRepository;
import com.excusasshark.service.mapper.MemeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de memes
 */
@Service
@RequiredArgsConstructor
public class MemeService {

    private final MemeRepository memeRepository;
    private final Random random = new Random();

    public Meme create(Meme meme) {
        return memeRepository.save(meme);
    }

    public MemeResponseDTO getById(Long id) {
        return memeRepository.findById(id)
                .map(MemeMapper::toResponse)
                .orElse(null);
    }

    public List<MemeResponseDTO> getAll() {
        return memeRepository.findAll()
                .stream()
                .map(MemeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<MemeResponseDTO> getActive() {
        return memeRepository.findByActiveTrue()
                .stream()
                .map(MemeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Meme getRandomMeme() {
        List<Meme> memes = memeRepository.findByActiveTrue();
        if (memes.isEmpty()) {
            return null;
        }
        int randomIndex = random.nextInt(memes.size());
        return memes.get(randomIndex);
    }

    public boolean delete(Long id) {
        if (memeRepository.existsById(id)) {
            memeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
