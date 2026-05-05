package com.kdudek.itemsapp.dto.response.storage;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class StorageSummaryDTO {

    Long id;
    String name;
    String note;
}
