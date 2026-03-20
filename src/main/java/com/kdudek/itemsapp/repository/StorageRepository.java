package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {

    @Query("SELECT s FROM Storage s LEFT JOIN FETCH s.internalStorages LEFT JOIN FETCH s.parent")
    List<Storage> findAll();

    @Query("SELECT s FROM Storage s LEFT JOIN FETCH s.internalStorages LEFT JOIN FETCH s.parent WHERE s.id = :id")
    Optional<Storage> findById(@Param("id") Long id);
}
