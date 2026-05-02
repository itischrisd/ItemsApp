package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
            SELECT b FROM Book b
                LEFT JOIN FETCH b.authors
                LEFT JOIN FETCH b.categories
                LEFT JOIN FETCH b.publisher
                LEFT JOIN FETCH b.storage
            WHERE b.id = :id""")
    Optional<Book> findByIdWithRelatedObjects(@Param("id") Long id);

    List<Book> findAllByAuthors_Id(Long authorId);

    List<Book> findAllByPublisher_Id(Long publisherId);
}
