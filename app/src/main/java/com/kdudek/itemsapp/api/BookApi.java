package com.kdudek.itemsapp.api;

import com.kdudek.itemsapp.controller.advice.RsqlSpecificationArgumentResolver.RsqlConstants;
import com.kdudek.itemsapp.dto.request.book.BookCreateDTO;
import com.kdudek.itemsapp.dto.request.book.BookUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookDetailsDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.entity.book.Book;
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

@Tag(name = "Books")
@RequestMapping("/api/books")
public interface BookApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Parameter(
            name = RsqlConstants.QUERY_PARAM_NAME,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    PageResponse<BookSummaryDTO> getAll(
            @Parameter(hidden = true) Specification<Book> specification,
            @ParameterObject Pageable pageable
    );

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookDetailsDTO getById(@PathVariable Long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BookDetailsDTO create(@RequestBody BookCreateDTO bookCreateDTO);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookDetailsDTO update(
            @PathVariable Long id,
            @RequestBody BookUpdateDTO bookUpdateDTO
    );

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);
}
