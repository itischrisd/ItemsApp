package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.dto.request.author.AuthorCreateDTO;
import com.kdudek.itemsapp.dto.request.author.AuthorUpdateDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorResponseDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.entity.book.Author;
import com.kdudek.itemsapp.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<AuthorResponseDTO> getAll(
            Specification<Author> specification,
            @ParameterObject Pageable pageable
    ) {
        return PageResponse.of(authorService.getAll(specification, pageable));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseDTO getById(@PathVariable Long id) {
        return authorService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponseDTO create(@RequestBody @Valid AuthorCreateDTO authorCreateDTO) {
        return authorService.create(authorCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseDTO update(
            @PathVariable Long id,
            @RequestBody @Valid AuthorUpdateDTO authorUpdateDTO
    ) {
        return authorService.update(id, authorUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<BookSummaryDTO> getBooksByAuthorId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    ) {
        return PageResponse.of(authorService.getBooksByAuthorId(id, pageable));
    }

    @PostMapping("/{authorId}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBookToAuthor(
            @PathVariable Long authorId,
            @PathVariable Long bookId
    ) {
        authorService.addBookToAuthor(authorId, bookId);
    }

    @DeleteMapping("/{authorId}/books/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookFromAuthor(
            @PathVariable Long authorId,
            @PathVariable Long bookId
    ) {
        authorService.removeBookFromAuthor(authorId, bookId);
    }
}
