package com.kdudek.itemsapp.entity;

import com.kdudek.itemsapp.entity.book.Book;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String note;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "parent_id")
    private Storage parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Storage> internalStorages;

    @OneToMany(mappedBy = "storage", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Book> books;

    @OneToMany(mappedBy = "storage", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Item> items;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime updatedAt;
    @Version
    private Integer version;

    @PreRemove
    private void orphanChildren() {
        internalStorages.forEach(internalStorage -> internalStorage.setParent(null));
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.setStorage(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.setStorage(null);
    }

    public void addItem(Item item) {
        this.items.add(item);
        item.setStorage(this);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
        item.setStorage(null);
    }
}
