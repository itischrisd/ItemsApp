package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.BookMapper;
import com.kdudek.itemsapp.dto.mapper.CategoryMapper;
import com.kdudek.itemsapp.dto.mapper.ItemMapper;
import com.kdudek.itemsapp.dto.request.category.CategoryCreateDTO;
import com.kdudek.itemsapp.dto.request.category.CategoryUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.category.CategoryResponseDTO;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import com.kdudek.itemsapp.entity.Category;
import com.kdudek.itemsapp.entity.Item;
import com.kdudek.itemsapp.entity.book.Book;
import com.kdudek.itemsapp.exception.RelatedResourceNotFoundException;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.repository.BookRepository;
import com.kdudek.itemsapp.repository.CategoryRepository;
import com.kdudek.itemsapp.repository.ItemRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public Page<CategoryResponseDTO> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::mapToDTO);
    }

    public CategoryResponseDTO getById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, id));
    }

    public CategoryResponseDTO create(@Valid CategoryCreateDTO categoryCreateDTO) {
        Category category = categoryMapper.mapToCategory(categoryCreateDTO);
        categoryRepository.save(category);
        return categoryMapper.mapToDTO(category);
    }

    public CategoryResponseDTO update(Long id, @Valid CategoryUpdateDTO categoryUpdateDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, id));
        categoryMapper.updateCategoryFromDTO(categoryUpdateDTO, category);
        categoryRepository.save(category);
        return categoryMapper.mapToDTO(category);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(Category.class, id);
        }
        categoryRepository.deleteById(id);
    }

    public Page<BookSummaryDTO> getBooksByCategoryId(Long id, Pageable pageable) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(Category.class, id);
        }
        return bookRepository.findAllByCategories_Id(id, pageable)
                .map(bookMapper::mapToSummaryDTO);
    }

    public void addBookToCategory(Long categoryId, Long bookId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, categoryId));
        Book book = bookRepository.findByIdWithCategories(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, bookId));
        book.getCategories().add(category);
        bookRepository.save(book);
    }

    public void removeBookFromCategory(Long categoryId, Long bookId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException(Category.class, categoryId);
        }
        Book book = bookRepository.findByIdWithCategories(bookId)
                .orElseThrow(() -> new RelatedResourceNotFoundException(Category.class, categoryId, Book.class, bookId));
        Category category = book.getCategories().stream()
                .filter(c -> c.getId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new RelatedResourceNotFoundException(Category.class, categoryId, Book.class, bookId));
        book.getCategories().remove(category);
        bookRepository.save(book);
    }

    public Page<ItemSummaryDTO> getItemsByCategoryId(Long id, Pageable pageable) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(Category.class, id);
        }
        return itemRepository.findAllByCategories_Id(id, pageable)
                .map(itemMapper::mapToSummaryDTO);
    }

    public void addItemToCategory(Long categoryId, Long itemId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, categoryId) );
        Item item = itemRepository.findByIdWithCategories(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(Item.class, itemId));
        item.getCategories().add(category);
        itemRepository.save(item);
    }

    public void removeItemFromCategory(Long categoryId, Long itemId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException(Category.class, categoryId);
        }
        Item item = itemRepository.findByIdWithCategories((itemId))
                .orElseThrow(() -> new RelatedResourceNotFoundException(Category.class, categoryId, Item.class, itemId));
        Category category = item.getCategories().stream()
                .filter(c -> c.getId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new RelatedResourceNotFoundException(Category.class, categoryId, Item.class, itemId));
        item.getCategories().remove(category);
        itemRepository.save(item);
    }
}
