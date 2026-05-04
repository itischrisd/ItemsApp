package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            SELECT c FROM Category c
                LEFT JOIN FETCH c.books
                LEFT JOIN FETCH c.items
            WHERE c.id = :id""")
    Optional<Category> findByIdWithRelatedObjects(@Param("id") Long id);

    @Query("""
            SELECT c FROM Category c
                LEFT JOIN FETCH c.books
            WHERE c.id = :id""")
    Optional<Category> findByIdWithBooks(@Param("id") Long id);

    @Query("""
            SELECT c FROM Category c
                LEFT JOIN FETCH c.items
            WHERE c.id = :id""")
    Optional<Category> findByIdWithItems(@Param("id") Long id);
}
