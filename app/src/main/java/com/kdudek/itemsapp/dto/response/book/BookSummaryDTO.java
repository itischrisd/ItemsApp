package com.kdudek.itemsapp.dto.response.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kdudek.itemsapp.dto.response.common.IdentifiableResource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class BookSummaryDTO implements IdentifiableResource {

    Long id;
    String title;
    Integer yearOfPublication;
    String coverType;
    String serialNumber;
    Integer editionNumber;
    String catalogNumber;
    String note;

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
