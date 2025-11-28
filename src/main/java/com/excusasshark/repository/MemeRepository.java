package com.excusasshark.repository;

import com.excusasshark.model.Meme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para entidad Meme
 */
@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {
    List<Meme> findByActiveTrue();
    List<Meme> findByCategory(String category);
}
