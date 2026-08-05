package com.kdudek.itemsapp.entity;

import com.kdudek.itemsapp.common.DomainLimits;
import com.kdudek.itemsapp.entity.book.Book;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String name;

    @Column(length = DomainLimits.DESCRIPTION)
    private String note;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Storage parent;

    @OneToMany(
            mappedBy = "parent",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private Set<Storage> internalStorages;

    @OneToMany(
            mappedBy = "storage",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private Set<Book> books;

    @OneToMany(
            mappedBy = "storage",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private Set<Item> items;

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
