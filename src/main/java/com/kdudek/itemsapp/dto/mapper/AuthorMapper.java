package com.kdudek.itemsapp.dto.mapper;

import com.kdudek.itemsapp.config.MapStructConfig;
import com.kdudek.itemsapp.dto.request.author.AuthorCreateDTO;
import com.kdudek.itemsapp.dto.request.author.AuthorUpdateDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorDetailsDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorSummaryDTO;
import com.kdudek.itemsapp.entity.book.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface AuthorMapper {

    AuthorSummaryDTO mapToSummaryDTO(Author author);

    AuthorDetailsDTO mapToDetailsDTO(Author author);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Author mapToAuthor(AuthorCreateDTO authorCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateAuthorFromDTO(AuthorUpdateDTO authorUpdateDTO, @MappingTarget Author author);
}
