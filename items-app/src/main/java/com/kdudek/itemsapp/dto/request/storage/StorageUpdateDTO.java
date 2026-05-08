package com.kdudek.itemsapp.dto.request.storage;

import com.kdudek.itemsapp.common.DomainLimits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageUpdateDTO {

    @NotBlank
    @Size(max = DomainLimits.LONG_NAME)
    private String name;
    @Size(max = DomainLimits.DESCRIPTION)
    private String note;
}
