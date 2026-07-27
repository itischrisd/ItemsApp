package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.book.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
