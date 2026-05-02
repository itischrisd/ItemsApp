package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.dto.request.author.AuthorCreateDTO;
import com.kdudek.itemsapp.dto.request.author.AuthorUpdateDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorDetailsDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorSummaryDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.service.AuthorService;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorSummaryDTO> getAll() {
        return authorService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDetailsDTO getById(@PathVariable Long id) {
        return authorService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDetailsDTO create(@RequestBody AuthorCreateDTO authorCreateDTO) {
        return authorService.create(authorCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDetailsDTO update(@PathVariable Long id, @RequestBody AuthorUpdateDTO authorUpdateDTO) {
        return authorService.update(id, authorUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    public List<BookSummaryDTO> getBooksByAuthorId(@PathVariable Long id) {
        return authorService.getBooksByAuthorId(id);
    }

    @PostMapping("/{authorId}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBookToAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        authorService.addBookToAuthor(authorId, bookId);
    }

    @DeleteMapping("/{authorId}/books/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookFromAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        authorService.removeBookFromAuthor(authorId, bookId);
    }
}
