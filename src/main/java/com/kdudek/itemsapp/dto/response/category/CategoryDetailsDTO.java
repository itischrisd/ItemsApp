package com.kdudek.itemsapp.dto.response.category;

import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CategoryDetailsDTO {

    Long id;
    String name;
    List<ItemSummaryDTO> items;
    List<BookSummaryDTO> books;
}
