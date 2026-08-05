package com.kdudek.itemsapp.api;

import com.kdudek.itemsapp.controller.advice.RsqlSpecificationArgumentResolver.RsqlConstants;
import com.kdudek.itemsapp.dto.request.category.CategoryCreateDTO;
import com.kdudek.itemsapp.dto.request.category.CategoryUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.category.CategoryResponseDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import com.kdudek.itemsapp.entity.Category;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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

@Tag(name = "Categories")
@RequestMapping("/api/categories")
public interface CategoryApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Parameter(
            name = RsqlConstants.QUERY_PARAM_NAME,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    PageResponse<CategoryResponseDTO> getAll(
            @Parameter(hidden = true) Specification<Category> specification,
            @ParameterObject Pageable pageable
    );

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryResponseDTO getById(@PathVariable Long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryResponseDTO create(@RequestBody CategoryCreateDTO categoryCreateDTO);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryResponseDTO update(
            @PathVariable Long id,
            @RequestBody CategoryUpdateDTO categoryUpdateDTO
    );

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    PageResponse<BookSummaryDTO> getBooksByCategoryId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    );

    @PostMapping("/{categoryId}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    void addBookToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long bookId
    );

    @DeleteMapping("/{categoryId}/books/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeBookFromCategory(
            @PathVariable Long categoryId,
            @PathVariable Long bookId
    );

    @GetMapping("/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    PageResponse<ItemSummaryDTO> getItemsByCategoryId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    );

    @PostMapping("/{categoryId}/items/{itemId}")
    @ResponseStatus(HttpStatus.CREATED)
    void addItemToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long itemId
    );

    @DeleteMapping("/{categoryId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeItemFromCategory(
            @PathVariable Long categoryId,
            @PathVariable Long itemId
    );
}
