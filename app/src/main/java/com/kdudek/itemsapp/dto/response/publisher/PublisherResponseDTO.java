package com.kdudek.itemsapp.dto.response.publisher;

import com.kdudek.itemsapp.dto.response.common.IdentifiableResource;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class PublisherResponseDTO implements IdentifiableResource {

    Long id;
    String name;
}
