package com.kdudek.itemsapp.dto.response.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageDetailsDTO {

    private Long id;
    private String name;
    private String note;
    private StorageSummaryDTO parentStorage;
    private List<StorageSummaryDTO> internalStorages;
}
