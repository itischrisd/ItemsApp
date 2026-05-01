package com.kdudek.itemsapp.repository;

import com.kdudek.itemsapp.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("""
            SELECT i FROM Item i
                LEFT JOIN FETCH i.categories
                LEFT JOIN FETCH i.storage
            WHERE i.id = :id""")
    Optional<Item> findByIdWithRelatedObjects(@Param("id") Long id);
}
