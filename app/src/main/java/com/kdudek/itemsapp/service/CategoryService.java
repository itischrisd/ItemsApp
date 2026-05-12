package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.BookMapper;
import com.kdudek.itemsapp.dto.mapper.CategoryMapper;
import com.kdudek.itemsapp.dto.mapper.ItemMapper;
import com.kdudek.itemsapp.dto.request.category.CategoryCreateDTO;
import com.kdudek.itemsapp.dto.request.category.CategoryUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.category.CategoryDetailsDTO;
import com.kdudek.itemsapp.dto.response.category.CategorySummaryDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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

    public List<CategorySummaryDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::mapToSummaryDTO)
                .toList();
    }

    public CategoryDetailsDTO getById(Long id) {
        return categoryRepository.findByIdWithRelatedObjects(id)
                .map(categoryMapper::mapToDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, id));
    }

    public CategoryDetailsDTO create(@Valid CategoryCreateDTO categoryCreateDTO) {
        Category category = categoryMapper.mapToCategory(categoryCreateDTO);
        categoryRepository.save(category);
        return categoryMapper.mapToDetailsDTO(category);
    }

    public CategoryDetailsDTO update(Long id, @Valid CategoryUpdateDTO categoryUpdateDTO) {
        Category category = categoryRepository.findByIdWithRelatedObjects(id)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, id));
        categoryMapper.updateCategoryFromDTO(categoryUpdateDTO, category);
        categoryRepository.save(category);
        return categoryMapper.mapToDetailsDTO(category);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(Category.class, id);
        }
        categoryRepository.deleteById(id);
    }

    public List<BookSummaryDTO> getBooksByCategoryId(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(Category.class, id);
        }
        return bookRepository.findAllByCategories_Id(id).stream()
                .map(bookMapper::mapToSummaryDTO)
                .toList();
    }

    public void addBookToCategory(Long categoryId, Long bookId) {
        Category category = categoryRepository.findByIdWithBooks(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, categoryId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, bookId));
        category.addBook(book);
        categoryRepository.save(category);
    }

    public void removeBookFromCategory(Long categoryId, Long bookId) {
        Category category = categoryRepository.findByIdWithBooks(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, categoryId));
        Book book = category.getBooks().stream()
                .filter(b -> b.getId().equals(bookId))
                .findAny()
                .orElseThrow(() -> new RelatedResourceNotFoundException(Category.class, categoryId, Book.class, bookId));
        category.removeBook(book);
        categoryRepository.save(category);
    }

    public List<ItemSummaryDTO> getItemsByCategoryId(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(Category.class, id);
        }
        return itemRepository.findAllByCategories_Id(id).stream()
                .map(itemMapper::mapToSummaryDTO)
                .toList();
    }

    public void addItemToCategory(Long categoryId, Long itemId) {
        Category category = categoryRepository.findByIdWithItems(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, categoryId));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(Item.class, itemId));
        category.addItem(item);
        categoryRepository.save(category);
    }

    public void removeItemFromCategory(Long categoryId, Long itemId) {
        Category category = categoryRepository.findByIdWithItems(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, categoryId));
        Item item = category.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findAny()
                .orElseThrow(() -> new RelatedResourceNotFoundException(Category.class, categoryId, Book.class, itemId));
        category.removeItem(item);
        categoryRepository.save(category);
    }
}
