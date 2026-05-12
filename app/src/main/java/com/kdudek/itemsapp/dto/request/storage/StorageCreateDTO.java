package com.kdudek.itemsapp.dto.request.storage;

import com.kdudek.itemsapp.entity.Storage;
import com.kdudek.itemsapp.common.DomainLimits;
import com.kdudek.itemsapp.validation.annotation.ExistsInDatabase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageCreateDTO {

    @NotBlank
    @Size(max = DomainLimits.LONG_NAME)
    private String name;
    @Size(max = DomainLimits.DESCRIPTION)
    private String note;
    @ExistsInDatabase(entity = Storage.class)
    private Long parentId;
}
