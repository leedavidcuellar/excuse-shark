package com.excusasshark.repository;

import com.excusasshark.model.Excuse;
import com.excusasshark.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para entidad Excuse
 */
@Repository
public interface ExcuseRepository extends JpaRepository<Excuse, Long> {
    List<Excuse> findByActiveTrue();
    List<Excuse> findByRoleTarget(RoleType roleType);
    List<Excuse> findByCreatedAtAfter(LocalDateTime dateTime);
}
