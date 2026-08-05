package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.api.CategoryApi;
import com.kdudek.itemsapp.dto.request.category.CategoryCreateDTO;
import com.kdudek.itemsapp.dto.request.category.CategoryUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.category.CategoryResponseDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import com.kdudek.itemsapp.entity.Category;
import com.kdudek.itemsapp.service.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    @Override
    public PageResponse<CategoryResponseDTO> getAll(Specification<Category> specification, Pageable pageable) {
        return PageResponse.of(categoryService.getAll(specification, pageable));
    }

    @Override
    public CategoryResponseDTO getById(Long id) {
        return categoryService.getById(id);
    }

    @Override
    public CategoryResponseDTO create(CategoryCreateDTO categoryCreateDTO, HttpServletResponse response) {
        CategoryResponseDTO created = categoryService.create(categoryCreateDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        response.setHeader(HttpHeaders.LOCATION, location.toString());
        return created;
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryUpdateDTO categoryUpdateDTO) {
        return categoryService.update(id, categoryUpdateDTO);
    }

    @Override
    public void delete(Long id) {
        categoryService.delete(id);
    }

    @Override
    public PageResponse<BookSummaryDTO> getBooksByCategoryId(Long id, Pageable pageable) {
        return PageResponse.of(categoryService.getBooksByCategoryId(id, pageable));
    }

    @Override
    public void addBookToCategory(Long categoryId, Long bookId) {
        categoryService.addBookToCategory(categoryId, bookId);
    }

    @Override
    public void removeBookFromCategory(Long categoryId, Long bookId) {
        categoryService.removeBookFromCategory(categoryId, bookId);
    }

    @Override
    public PageResponse<ItemSummaryDTO> getItemsByCategoryId(Long id, Pageable pageable) {
        return PageResponse.of(categoryService.getItemsByCategoryId(id, pageable));
    }

    @Override
    public void addItemToCategory(Long categoryId, Long itemId) {
        categoryService.addItemToCategory(categoryId, itemId);
    }

    @Override
    public void removeItemFromCategory(Long categoryId, Long itemId) {
        categoryService.removeItemFromCategory(categoryId, itemId);
    }
}
