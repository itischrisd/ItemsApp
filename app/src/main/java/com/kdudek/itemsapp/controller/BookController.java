package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.api.BookApi;
import com.kdudek.itemsapp.dto.request.book.BookCreateDTO;
import com.kdudek.itemsapp.dto.request.book.BookUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookDetailsDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.entity.book.Book;
import com.kdudek.itemsapp.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController implements BookApi {

    private final BookService bookService;

    @Override
    public PageResponse<BookSummaryDTO> getAll(Specification<Book> specification, Pageable pageable) {
        return PageResponse.of(bookService.getAll(specification, pageable));
    }

    @Override
    public BookDetailsDTO getById(Long id) {
        return bookService.getById(id);
    }

    @Override
    public BookDetailsDTO create(BookCreateDTO bookCreateDTO) {
        return bookService.create(bookCreateDTO);
    }

    @Override
    public BookDetailsDTO update(Long id, BookUpdateDTO bookUpdateDTO) {
        return bookService.update(id, bookUpdateDTO);
    }

    @Override
    public void delete(Long id) {
        bookService.delete(id);
    }
}
