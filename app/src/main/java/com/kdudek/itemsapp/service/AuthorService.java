package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.AuthorMapper;
import com.kdudek.itemsapp.dto.mapper.BookMapper;
import com.kdudek.itemsapp.dto.request.author.AuthorCreateDTO;
import com.kdudek.itemsapp.dto.request.author.AuthorUpdateDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorResponseDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.entity.book.Author;
import com.kdudek.itemsapp.entity.book.Book;
import com.kdudek.itemsapp.exception.RelatedResourceNotFoundException;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.repository.AuthorRepository;
import com.kdudek.itemsapp.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Page<AuthorResponseDTO> getAll(Specification<Author> specification, Pageable pageable) {
        return authorRepository.findAll(specification, pageable)
                .map(authorMapper::maptoDTO);
    }

    public AuthorResponseDTO getById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::maptoDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Author.class, id));
    }

    public AuthorResponseDTO create(@Valid AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.mapToAuthor(authorCreateDTO);
        authorRepository.save(author);
        return authorMapper.maptoDTO(author);
    }

    public AuthorResponseDTO update(Long id, @Valid AuthorUpdateDTO authorUpdateDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Author.class, id));
        authorMapper.updateAuthorFromDTO(authorUpdateDTO, author);
        authorRepository.save(author);
        return authorMapper.maptoDTO(author);
    }

    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException(Author.class, id);
        }
        authorRepository.deleteById(id);
    }

    public Page<BookSummaryDTO> getBooksByAuthorId(Long id, Pageable pageable) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException(Author.class, id);
        }
        return bookRepository.findAllByAuthors_Id(id, pageable)
                .map(bookMapper::mapToSummaryDTO);
    }

    public void addBookToAuthor(Long authorId, Long bookId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(Author.class, authorId));
        Book book = bookRepository.findByIdWithAuthors(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, bookId));
        book.getAuthors().add(author);
        bookRepository.save(book);
    }

    public void removeBookFromAuthor(Long authorId, Long bookId) {
        if (!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException(Author.class, authorId);
        }
        Book book = bookRepository.findByIdWithAuthors(bookId)
                .orElseThrow(() -> new RelatedResourceNotFoundException(Author.class, authorId, Book.class, bookId));
        Author author = book.getAuthors().stream()
                .filter(a -> a.getId().equals(authorId))
                .findFirst()
                .orElseThrow(() -> new RelatedResourceNotFoundException(Author.class, authorId, Book.class, bookId));
        book.getAuthors().remove(author);
        bookRepository.save(book);
    }
}
