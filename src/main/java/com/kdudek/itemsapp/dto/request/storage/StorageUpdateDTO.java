package com.kdudek.itemsapp.dto.request.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageUpdateDTO {

    private String name;
    private String note;
}
