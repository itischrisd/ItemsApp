package com.kdudek.itemsapp.entity.book;

import com.kdudek.itemsapp.common.DomainLimits;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
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
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            length = DomainLimits.SHORT_NAME,
            nullable = false
    )
    private String name;

    @Column(length = DomainLimits.SHORT_NAME)
    private String surname;

    @ManyToMany(mappedBy = "authors", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Book> books;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime updatedAt;
    @Version
    private Integer version;

    @PreRemove
    private void preRemove() {
        books.forEach(book -> book.getAuthors().remove(this));
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getAuthors().remove(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getAuthors().remove(this);
    }
}
