package com.kdudek.itemsapp.dto.request.publisher;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class PublisherCreateDTO {

    String name;
}
