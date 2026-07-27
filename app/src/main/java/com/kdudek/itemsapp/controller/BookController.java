package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.dto.request.book.BookCreateDTO;
import com.kdudek.itemsapp.dto.request.book.BookUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookDetailsDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<BookSummaryDTO> getAll(@ParameterObject Pageable pageable) {
        return PageResponse.of(bookService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDetailsDTO getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDetailsDTO create(@RequestBody @Valid BookCreateDTO bookCreateDTO) {
        return bookService.create(bookCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDetailsDTO update(
            @PathVariable Long id,
            @RequestBody @Valid BookUpdateDTO bookUpdateDTO
    ) {
        return bookService.update(id, bookUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
