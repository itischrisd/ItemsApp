package com.kdudek.itemsapp.dto.mapper;

import com.kdudek.itemsapp.dto.request.book.BookCreateDTO;
import com.kdudek.itemsapp.dto.request.book.BookUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookDetailsDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.entity.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = ReferenceTranslator.class,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface BookMapper {

    BookSummaryDTO mapToSummaryDTO(Book book);

    BookDetailsDTO mapToDetailsDTO(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "authors", source = "authorsIds", qualifiedByName = "idToReference")
    @Mapping(target = "categories", source = "categoriesIds", qualifiedByName = "idToReference")
    @Mapping(target = "publisher", source = "publisherId", qualifiedByName = "idToReference")
    @Mapping(target = "storage", source = "storageId", qualifiedByName = "idToReference")
    Book mapToBook(BookCreateDTO bookCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "storage", ignore = true)
    void updateBookFromDTO(BookUpdateDTO bookUpdateDTO, @MappingTarget Book book);
}
