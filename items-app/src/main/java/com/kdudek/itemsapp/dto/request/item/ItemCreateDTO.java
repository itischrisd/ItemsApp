package com.kdudek.itemsapp.dto.request.item;

import com.kdudek.itemsapp.entity.Category;
import com.kdudek.itemsapp.entity.Storage;
import com.kdudek.itemsapp.common.DomainLimits;
import com.kdudek.itemsapp.validation.annotation.ExistsInDatabase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Value
@Builder
@Jacksonized
public class ItemCreateDTO {

    @NotBlank
    @Size(max = DomainLimits.LONG_NAME)
    String description;
    @Size(max = DomainLimits.SHORT_TEXT)
    String note;
    @ExistsInDatabase(entity = Category.class)
    Set<Integer> categoriesIds;
    @ExistsInDatabase(entity = Storage.class)
    Integer storageId;
}
