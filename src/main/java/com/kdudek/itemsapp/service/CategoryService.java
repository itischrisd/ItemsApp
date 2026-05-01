package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.CategoryMapper;
import com.kdudek.itemsapp.dto.request.category.CategoryCreateDTO;
import com.kdudek.itemsapp.dto.request.category.CategoryUpdateDTO;
import com.kdudek.itemsapp.dto.response.category.CategoryDetailsDTO;
import com.kdudek.itemsapp.dto.response.category.CategorySummaryDTO;
import com.kdudek.itemsapp.entity.Category;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategorySummaryDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::mapToSummaryDTO)
                .toList();
    }

    public CategoryDetailsDTO getById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::mapToDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, id));
    }

    public CategoryDetailsDTO create(CategoryCreateDTO categoryCreateDTO) {
        Category category = categoryMapper.mapToCategory(categoryCreateDTO);
        categoryRepository.save(category);
        return categoryMapper.mapToDetailsDTO(category);
    }

    public CategoryDetailsDTO update(Long id, CategoryUpdateDTO categoryUpdateDTO) {
        Category category = categoryRepository.findById(id)
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
}
