package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.book.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    boolean existsByIdAndVersion(Long id, Integer version);
}
