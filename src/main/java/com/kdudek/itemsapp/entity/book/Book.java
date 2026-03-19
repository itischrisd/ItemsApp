package com.kdudek.itemsapp.entity.book;

import com.kdudek.itemsapp.entity.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private Year yearOfPublication;
    private String coverType;
    private String serialNumber;
    private Integer editionNumber;
    private String catalogNumber;
    private String note;

    @ManyToMany
    @JoinTable(name = "BookAuthor")
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(name = "BookCategory")
    private Set<Category> categories;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
}
