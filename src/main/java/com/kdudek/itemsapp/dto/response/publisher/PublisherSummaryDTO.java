package com.kdudek.itemsapp.dto.response.publisher;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class PublisherSummaryDTO {

    Long id;
    String name;
}
