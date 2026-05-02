package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.AuthorMapper;
import com.kdudek.itemsapp.dto.mapper.BookMapper;
import com.kdudek.itemsapp.dto.request.author.AuthorCreateDTO;
import com.kdudek.itemsapp.dto.request.author.AuthorUpdateDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorDetailsDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorSummaryDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.entity.book.Author;
import com.kdudek.itemsapp.entity.book.Book;
import com.kdudek.itemsapp.exception.RelatedResourceNotFoundException;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.repository.AuthorRepository;
import com.kdudek.itemsapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    public List<AuthorSummaryDTO> getAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::mapToSummaryDTO)
                .toList();
    }

    public AuthorDetailsDTO getById(Long id) {
        return authorRepository.findByIdWithRelatedObjects(id)
                .map(authorMapper::mapToDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Author.class, id));
    }

    public AuthorDetailsDTO create(AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.mapToAuthor(authorCreateDTO);
        authorRepository.save(author);
        return authorMapper.mapToDetailsDTO(author);
    }

    public AuthorDetailsDTO update(Long id, AuthorUpdateDTO authorUpdateDTO) {
        Author author = authorRepository.findByIdWithRelatedObjects(id)
                .orElseThrow(() -> new ResourceNotFoundException(Author.class, id));
        authorMapper.updateAuthorFromDTO(authorUpdateDTO, author);
        authorRepository.save(author);
        return authorMapper.mapToDetailsDTO(author);
    }

    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException(Author.class, id);
        }
        authorRepository.deleteById(id);
    }

    public List<BookSummaryDTO> getBooksByAuthorId(Long id) {
        return bookRepository.findAllByAuthors_Id(id).stream()
                .map(bookMapper::mapToSummaryDTO)
                .toList();
    }

    public void addBookToAuthor(Long authorId, Long bookId) {
        Author author = authorRepository.findByIdWithRelatedObjects(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(Author.class, authorId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, bookId));
        author.addBook(book);
        authorRepository.save(author);
    }

    public void removeBookFromAuthor(Long authorId, Long bookId) {
        Author author = authorRepository.findByIdWithRelatedObjects(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(Author.class, authorId));
        Book book = author.getBooks().stream()
                .filter(b -> b.getId().equals(bookId))
                .findAny()
                .orElseThrow(() -> new RelatedResourceNotFoundException(Author.class, authorId, Book.class, bookId));
        author.removeBook(book);
        authorRepository.save(author);
    }
}
