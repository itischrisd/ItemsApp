package com.kdudek.itemsapp.dto.response.category;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryDetailsDTO {

    Long id;
    String name;
}
