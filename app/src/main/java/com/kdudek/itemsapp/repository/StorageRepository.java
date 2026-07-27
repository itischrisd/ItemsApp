package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {

    @Query("""
            SELECT s FROM Storage s
            WHERE s.id = :id""")
    @EntityGraph(attributePaths = {"parent"})
    Optional<Storage> findByIdWithParent(@Param("id") Long id);

    Page<Storage> findAllByParent_Id(Long parentId, Pageable pageable);
}
