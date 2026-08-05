package com.kdudek.itemsapp.dto.response.category;

import com.kdudek.itemsapp.dto.response.common.IdentifiableResource;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryResponseDTO implements IdentifiableResource {

    Long id;
    String name;
}
