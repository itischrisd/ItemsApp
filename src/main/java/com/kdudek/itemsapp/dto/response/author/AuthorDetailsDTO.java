package com.kdudek.itemsapp.dto.response.author;

import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Value
@Builder
@Jacksonized
public class AuthorDetailsDTO {

    Long id;
    String name;
    String surname;
    Set<BookSummaryDTO> books;
}
