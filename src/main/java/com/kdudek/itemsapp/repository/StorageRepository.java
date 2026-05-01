package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {

    @Query("""
            SELECT s FROM Storage s
                LEFT JOIN FETCH s.parent
                LEFT JOIN FETCH s.internalStorages
            WHERE s.id = :id""")
    Optional<Storage> findByIdWithRelatedObjects(@Param("id") Long id);

    Optional<Storage> findById_AndParent_Id(Long id, Long parentId);

    List<Storage> findAllByParent_Id(Long parentId);
}
