package com.kdudek.itemsapp.api;

import com.kdudek.itemsapp.controller.advice.RsqlSpecificationArgumentResolver.RsqlConstants;
import com.kdudek.itemsapp.dto.request.author.AuthorCreateDTO;
import com.kdudek.itemsapp.dto.request.author.AuthorUpdateDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorResponseDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.entity.book.Author;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Authors")
@RequestMapping("/api/authors")
public interface AuthorApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Parameter(
            name = RsqlConstants.QUERY_PARAM_NAME,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    PageResponse<AuthorResponseDTO> getAll(
            @Parameter(hidden = true) Specification<Author> specification,
            @ParameterObject Pageable pageable
    );

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    AuthorResponseDTO getById(@PathVariable Long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AuthorResponseDTO create(@RequestBody AuthorCreateDTO authorCreateDTO);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    AuthorResponseDTO update(
            @PathVariable Long id,
            @RequestBody AuthorUpdateDTO authorUpdateDTO
    );

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    PageResponse<BookSummaryDTO> getBooksByAuthorId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    );

    @PostMapping("/{authorId}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    void addBookToAuthor(
            @PathVariable Long authorId,
            @PathVariable Long bookId
    );

    @DeleteMapping("/{authorId}/books/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeBookFromAuthor(
            @PathVariable Long authorId,
            @PathVariable Long bookId
    );
}
