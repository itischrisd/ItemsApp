package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.book.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("""
            SELECT p FROM Publisher p
                LEFT JOIN FETCH p.books
            WHERE p.id = :id""")
    Optional<Publisher> findByIdWithRelatedObjects(@Param("id") Long id);
}
