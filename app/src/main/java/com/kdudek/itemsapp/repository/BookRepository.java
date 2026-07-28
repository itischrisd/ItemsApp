package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query("""
            SELECT b FROM Book b
            WHERE b.id = :id""")
    @EntityGraph(attributePaths = {"authors", "categories", "publisher", "storage"})
    Optional<Book> findByIdWithRelatedObjects(@Param("id") Long id);

    @Query("""
            SELECT b FROM Book b
            WHERE b.id = :id""")
    @EntityGraph(attributePaths = {"authors"})
    Optional<Book> findByIdWithAuthors(@Param("id") Long id);

    @Query("""
            SELECT b FROM Book b
            WHERE b.id = :id""")
    @EntityGraph(attributePaths = {"categories"})
    Optional<Book> findByIdWithCategories(@Param("id") Long id);

    @Query("""
            SELECT b FROM Book b
            WHERE b.id = :id""")
    @EntityGraph(attributePaths = {"publisher"})
    Optional<Book> findByIdWithPublisher(@Param("id") Long id);

    @Query("""
            SELECT b FROM Book b
            WHERE b.id = :id""")
    @EntityGraph(attributePaths = {"storage"})
    Optional<Book> findByIdWithStorage(@Param("id") Long id);

    Page<Book> findAllByAuthors_Id(Long authorId, Pageable pageable);

    Page<Book> findAllByPublisher_Id(Long publisherId, Pageable pageable);

    Page<Book> findAllByCategories_Id(Long categoryId, Pageable pageable);

    Page<Book> findAllByStorage_Id(Long storageId, Pageable pageable);
}
