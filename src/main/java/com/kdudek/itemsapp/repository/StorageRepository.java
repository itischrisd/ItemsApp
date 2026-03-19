package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {
}
