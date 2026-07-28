package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.dto.request.category.CategoryCreateDTO;
import com.kdudek.itemsapp.dto.request.category.CategoryUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.category.CategoryResponseDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import com.kdudek.itemsapp.entity.Category;
import com.kdudek.itemsapp.service.CategoryService;
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
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<CategoryResponseDTO> getAll(
            Specification<Category> specification,
            @ParameterObject Pageable pageable
    ) {
        return PageResponse.of(categoryService.getAll(specification, pageable));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDTO getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDTO create(@RequestBody @Valid CategoryCreateDTO categoryCreateDTO) {
        return categoryService.create(categoryCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDTO update(
            @PathVariable Long id,
            @RequestBody @Valid CategoryUpdateDTO categoryUpdateDTO
    ) {
        return categoryService.update(id, categoryUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<BookSummaryDTO> getBooksByCategoryId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    ) {
        return PageResponse.of(categoryService.getBooksByCategoryId(id, pageable));
    }

    @PostMapping("/{categoryId}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBookToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long bookId
    ) {
        categoryService.addBookToCategory(categoryId, bookId);
    }

    @DeleteMapping("/{categoryId}/books/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookFromCategory(
            @PathVariable Long categoryId,
            @PathVariable Long bookId
    ) {
        categoryService.removeBookFromCategory(categoryId, bookId);
    }

    @GetMapping("/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ItemSummaryDTO> getItemsByCategoryId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    ) {
        return PageResponse.of(categoryService.getItemsByCategoryId(id, pageable));
    }

    @PostMapping("/{categoryId}/items/{itemId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItemToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long itemId
    ) {
        categoryService.addItemToCategory(categoryId, itemId);
    }

    @DeleteMapping("/{categoryId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItemFromCategory(
            @PathVariable Long categoryId,
            @PathVariable Long itemId
    ) {
        categoryService.removeItemFromCategory(categoryId, itemId);
    }
}
