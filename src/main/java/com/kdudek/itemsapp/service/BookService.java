package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.BookMapper;
import com.kdudek.itemsapp.dto.request.book.BookCreateDTO;
import com.kdudek.itemsapp.dto.request.book.BookUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookDetailsDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.entity.book.Book;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookSummaryDTO> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::mapToSummaryDTO)
                .toList();
    }

    public BookDetailsDTO getById(Long id) {
        return bookRepository.findByIdWithRelatedObjects(id)
                .map(bookMapper::mapToDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, id));
    }

    public BookDetailsDTO create(BookCreateDTO bookCreateDTO) {
        Book book = bookMapper.mapToBook(bookCreateDTO);
        bookRepository.save(book);
        return bookMapper.mapToDetailsDTO(book);
    }

    public BookDetailsDTO update(Long id, BookUpdateDTO bookUpdateDTO) {
        Book book = bookRepository.findByIdWithRelatedObjects(id)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, id));
        bookMapper.updateBookFromDTO(bookUpdateDTO, book);
        bookRepository.save(book);
        return bookMapper.mapToDetailsDTO(book);
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException(Book.class, id);
        }
        bookRepository.deleteById(id);
    }
}
