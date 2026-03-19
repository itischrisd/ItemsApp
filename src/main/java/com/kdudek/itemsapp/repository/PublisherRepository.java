package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.Item;
import com.kdudek.itemsapp.entity.book.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
