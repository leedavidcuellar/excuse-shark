package com.excusasshark.repository;

import com.excusasshark.model.Fragment;
import com.excusasshark.model.FragmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para entidad Fragment
 */
@Repository
public interface FragmentRepository extends JpaRepository<Fragment, Long> {
    List<Fragment> findByType(FragmentType type);
    List<Fragment> findByTypeAndActiveTrue(FragmentType type);
    List<Fragment> findByCategory(String category);
    List<Fragment> findByActiveTrue();
}
