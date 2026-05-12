package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.book.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("""
            SELECT a FROM Author a
                LEFT JOIN FETCH a.books
            WHERE a.id = :id""")
    Optional<Author> findByIdWithRelatedObjects(@Param("id") Long id);
}
