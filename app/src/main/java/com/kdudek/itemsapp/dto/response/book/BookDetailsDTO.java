package com.kdudek.itemsapp.dto.response.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kdudek.itemsapp.dto.response.author.AuthorResponseDTO;
import com.kdudek.itemsapp.dto.response.category.CategoryResponseDTO;
import com.kdudek.itemsapp.dto.response.common.IdentifiableResource;
import com.kdudek.itemsapp.dto.response.common.VersionedResource;
import com.kdudek.itemsapp.dto.response.publisher.PublisherResponseDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageSummaryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class BookDetailsDTO implements IdentifiableResource, VersionedResource {

    Long id;
    String title;
    Integer yearOfPublication;
    String coverType;
    String serialNumber;
    Integer editionNumber;
    String catalogNumber;
    String note;
    Set<AuthorResponseDTO> authors;
    Set<CategoryResponseDTO> categories;
    PublisherResponseDTO publisher;
    StorageSummaryDTO storage;

    @JsonIgnore
    @Schema(hidden = true)
    Integer version;
    @JsonIgnore
    @Schema(hidden = true)
    Instant createdAt;
    @JsonIgnore
    @Schema(hidden = true)
    Instant updatedAt;
}
