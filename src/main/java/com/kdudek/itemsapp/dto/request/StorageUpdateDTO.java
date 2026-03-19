package com.kdudek.itemsapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageUpdateDTO {

    private Long id;
    private String name;
    private String note;
    private Long parentId;
}
