package com.excusasshark.repository;

import com.excusasshark.model.Law;
import com.excusasshark.model.LawType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para entidad Law
 */
@Repository
public interface LawRepository extends JpaRepository<Law, Long> {
    List<Law> findByType(LawType type);
    List<Law> findByActiveTrue();
}
