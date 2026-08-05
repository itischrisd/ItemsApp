package com.kdudek.itemsapp.entity.book;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "publisher",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private Set<Book> books;

    @Version
    @Column(nullable = false)
    private Integer version;
    @CreationTimestamp
    @Column(
            updatable = false,
            nullable = false
    )
    private Instant createdAt;
    @UpdateTimestamp(source = SourceType.DB)
    private Instant updatedAt;
}
