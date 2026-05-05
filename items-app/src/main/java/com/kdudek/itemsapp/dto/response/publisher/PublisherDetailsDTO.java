package com.kdudek.itemsapp.dto.response.publisher;

import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Value
@Builder
@Jacksonized
public class PublisherDetailsDTO {

    Long id;
    String name;
    Set<BookSummaryDTO> books;
}
