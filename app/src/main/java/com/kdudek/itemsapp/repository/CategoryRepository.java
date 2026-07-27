package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
