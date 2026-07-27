package com.kdudek.itemsapp.entity.book;

import com.kdudek.itemsapp.common.DomainLimits;
import com.kdudek.itemsapp.entity.Category;
import com.kdudek.itemsapp.entity.Storage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
import java.time.Year;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private Year yearOfPublication;

    @Column(length = DomainLimits.SHORT_NAME)
    private String coverType;

    @Column(length = DomainLimits.CODE)
    private String serialNumber;

    private Integer editionNumber;

    @Column(length = DomainLimits.CODE)
    private String catalogNumber;

    @Column(length = DomainLimits.DESCRIPTION)
    private String note;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Author> authors;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Category> categories;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Publisher publisher;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Storage storage;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime updatedAt;
    @Version
    private Integer version;
}
