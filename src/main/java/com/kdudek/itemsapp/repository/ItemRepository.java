package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
