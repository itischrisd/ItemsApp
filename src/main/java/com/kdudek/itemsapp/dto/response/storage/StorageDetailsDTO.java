package com.kdudek.itemsapp.dto.response.storage;

import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class StorageDetailsDTO {

    Long id;
    String name;
    String note;
    StorageSummaryDTO parentStorage;
    List<StorageSummaryDTO> internalStorages;
    List<ItemSummaryDTO> items;
    List<BookSummaryDTO> books;
}
