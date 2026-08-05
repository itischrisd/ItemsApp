package com.kdudek.itemsapp.dto.response.publisher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kdudek.itemsapp.dto.response.common.IdentifiableResource;
import com.kdudek.itemsapp.dto.response.common.VersionedResource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Value
@Builder
@Jacksonized
public class PublisherResponseDTO implements IdentifiableResource, VersionedResource {

    Long id;
    String name;

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
