package com.kdudek.itemsapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageResponseDTO {

    private Long id;
    private String name;
    private String note;
    private Long parentId;
}
