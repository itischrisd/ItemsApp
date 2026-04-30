package com.kdudek.itemsapp.dto.response.storage;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class StorageDetailsDTO {

    Long id;
    String name;
    String note;
    StorageSummaryDTO parentStorage;
    List<StorageSummaryDTO> internalStorages;
}
