package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("""
            SELECT i FROM Item i
            WHERE i.id = :id""")
    @EntityGraph(attributePaths = {"categories", "storage"})
    Optional<Item> findByIdWithRelatedObjects(@Param("id") Long id);

    @Query("""
            SELECT i FROM Item i
            WHERE i.id = :id""")
    @EntityGraph(attributePaths = {"categories"})
    Optional<Item> findByIdWithCategories(@Param("id") Long id);

    @Query("""
            SELECT i FROM Item i
            WHERE i.id = :id""")
    @EntityGraph(attributePaths = {"storage"})
    Optional<Item> findByIdWithStorage(@Param("id") Long id);

    Page<Item> findAllByCategories_Id(Long categoryId, Pageable pageable);

    Page<Item> findAllByStorage_Id(Long storageId, Pageable pageable);
}
