package com.kdudek.itemsapp.dto.request.item;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ItemUpdateDTO {

    String description;
    String note;
}
