package com.kdudek.itemsapp.entity;

import com.kdudek.itemsapp.entity.book.Book;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String name;

    @ManyToMany(
            mappedBy = "categories",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Item> items;

    @ManyToMany(
            mappedBy = "categories",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Book> books;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime updatedAt;
    @Version
    private Integer version;
}
