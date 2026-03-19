package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
