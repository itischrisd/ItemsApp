package com.kdudek.itemsapp.dto.request.item;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Value
@Builder
@Jacksonized
public class ItemCreateDTO {

    String description;
    String note;
    Set<Integer> categoriesIds;
    Integer storageId;
}
