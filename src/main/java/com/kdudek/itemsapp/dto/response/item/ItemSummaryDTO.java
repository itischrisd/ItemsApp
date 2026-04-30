package com.kdudek.itemsapp.dto.response.item;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ItemSummaryDTO {

    Long id;
    String description;
    String note;
}
