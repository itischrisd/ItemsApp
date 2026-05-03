package com.kdudek.itemsapp.dto.mapper;

import com.kdudek.itemsapp.config.MapStructConfig;
import com.kdudek.itemsapp.dto.request.category.CategoryCreateDTO;
import com.kdudek.itemsapp.dto.request.category.CategoryUpdateDTO;
import com.kdudek.itemsapp.dto.response.category.CategoryDetailsDTO;
import com.kdudek.itemsapp.dto.response.category.CategorySummaryDTO;
import com.kdudek.itemsapp.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface CategoryMapper {

    CategorySummaryDTO mapToSummaryDTO(Category category);

    CategoryDetailsDTO mapToDetailsDTO(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "items", ignore = true)
    Category mapToCategory(CategoryCreateDTO categoryCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "items", ignore = true)
    void updateCategoryFromDTO(CategoryUpdateDTO categoryUpdateDTO, @MappingTarget Category category);
}
