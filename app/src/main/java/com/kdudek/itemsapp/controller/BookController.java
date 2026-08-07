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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController implements BookApi {

    private final BookService bookService;

    @Override
    public PageResponse<BookSummaryDTO> getAll(Specification<Book> specification, Pageable pageable) {
        return PageResponse.of(bookService.getAll(specification, pageable));
    }

    @Override
    public BookDetailsDTO getById(Long id, Integer ifNoneMatch) {
        return bookService.getById(id, ifNoneMatch);
    }

    @Override
    public BookDetailsDTO create(BookCreateDTO bookCreateDTO) {
        return bookService.create(bookCreateDTO);
    }

    @Override
    public BookDetailsDTO update(Long id, BookUpdateDTO bookUpdateDTO, Integer ifMatch) {
        return bookService.update(id, bookUpdateDTO, ifMatch);
    }

    @Override
    public void delete(Long id, Integer ifMatch) {
        bookService.delete(id, ifMatch);
    }
}
