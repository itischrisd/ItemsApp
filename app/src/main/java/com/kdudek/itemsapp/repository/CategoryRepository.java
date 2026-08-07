package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    boolean existsByIdAndVersion(Long id, Integer version);
}
