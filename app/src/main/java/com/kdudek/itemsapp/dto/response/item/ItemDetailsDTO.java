package com.kdudek.itemsapp.dto.response.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kdudek.itemsapp.dto.response.common.IdentifiableResource;
import com.kdudek.itemsapp.dto.response.category.CategoryResponseDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageSummaryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class ItemDetailsDTO implements IdentifiableResource {

    Long id;
    String description;
    String note;
    Set<CategoryResponseDTO> categories;
    StorageSummaryDTO storage;

    @JsonIgnore
    @Schema(hidden = true)
    Integer version;
    @JsonIgnore
    @Schema(hidden = true)
    LocalDateTime createdAt;
    @JsonIgnore
    @Schema(hidden = true)
    LocalDateTime updatedAt;
}
