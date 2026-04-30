package com.kdudek.itemsapp.dto.response.item;

import com.kdudek.itemsapp.dto.response.category.CategorySummaryDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageSummaryDTO;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Value
@Builder
@Jacksonized
public class ItemDetailsDTO {

    Long id;
    String description;
    String note;
    Set<CategorySummaryDTO> categories;
    StorageSummaryDTO storage;
}
