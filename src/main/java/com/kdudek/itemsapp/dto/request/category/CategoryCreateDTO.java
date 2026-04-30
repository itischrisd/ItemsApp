package com.kdudek.itemsapp.dto.request.category;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryCreateDTO {

    String name;
}
