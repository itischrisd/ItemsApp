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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    @Override
    public PageResponse<CategoryResponseDTO> getAll(Specification<Category> specification, Pageable pageable) {
        return PageResponse.of(categoryService.getAll(specification, pageable));
    }

    @Override
    public CategoryResponseDTO getById(Long id, Integer ifNoneMatch) {
        return categoryService.getById(id, ifNoneMatch);
    }

    @Override
    public CategoryResponseDTO create(CategoryCreateDTO categoryCreateDTO) {
        return categoryService.create(categoryCreateDTO);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryUpdateDTO categoryUpdateDTO, Integer ifMatch) {
        return categoryService.update(id, categoryUpdateDTO, ifMatch);
    }

    @Override
    public void delete(Long id, Integer ifMatch) {
        categoryService.delete(id, ifMatch);
    }

    @Override
    public PageResponse<BookSummaryDTO> getBooksByCategoryId(Long id, Pageable pageable) {
        return PageResponse.of(categoryService.getBooksByCategoryId(id, pageable));
    }

    @Override
    public void addBookToCategory(Long categoryId, Long bookId, Integer ifMatch) {
        categoryService.addBookToCategory(categoryId, bookId, ifMatch);
    }

    @Override
    public void removeBookFromCategory(Long categoryId, Long bookId, Integer ifMatch) {
        categoryService.removeBookFromCategory(categoryId, bookId, ifMatch);
    }

    @Override
    public PageResponse<ItemSummaryDTO> getItemsByCategoryId(Long id, Pageable pageable) {
        return PageResponse.of(categoryService.getItemsByCategoryId(id, pageable));
    }

    @Override
    public void addItemToCategory(Long categoryId, Long itemId, Integer ifMatch) {
        categoryService.addItemToCategory(categoryId, itemId, ifMatch);
    }

    @Override
    public void removeItemFromCategory(Long categoryId, Long itemId, Integer ifMatch) {
        categoryService.removeItemFromCategory(categoryId, itemId, ifMatch);
    }
}
