package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.api.AuthorApi;
import com.kdudek.itemsapp.dto.request.author.AuthorCreateDTO;
import com.kdudek.itemsapp.dto.request.author.AuthorUpdateDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorResponseDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.entity.book.Author;
import com.kdudek.itemsapp.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorController implements AuthorApi {

    private final AuthorService authorService;

    @Override
    public PageResponse<AuthorResponseDTO> getAll(Specification<Author> specification, Pageable pageable) {
        return PageResponse.of(authorService.getAll(specification, pageable));
    }

    @Override
    public AuthorResponseDTO getById(Long id) {
        return authorService.getById(id);
    }

    @Override
    public AuthorResponseDTO create(AuthorCreateDTO authorCreateDTO) {
        return authorService.create(authorCreateDTO);
    }

    @Override
    public AuthorResponseDTO update(Long id, AuthorUpdateDTO authorUpdateDTO) {
        return authorService.update(id, authorUpdateDTO);
    }

    @Override
    public void delete(Long id) {
        authorService.delete(id);
    }

    @Override
    public PageResponse<BookSummaryDTO> getBooksByAuthorId(Long id, Pageable pageable) {
        return PageResponse.of(authorService.getBooksByAuthorId(id, pageable));
    }

    @Override
    public void addBookToAuthor(Long authorId, Long bookId) {
        authorService.addBookToAuthor(authorId, bookId);
    }

    @Override
    public void removeBookFromAuthor(Long authorId, Long bookId) {
        authorService.removeBookFromAuthor(authorId, bookId);
    }
}
