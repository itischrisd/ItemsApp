package com.kdudek.itemsapp.dto.request.book;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Year;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class BookCreateDTO {

    String title;
    Year yearOfPublication;
    String coverType;
    String serialNumber;
    Integer editionNumber;
    String catalogNumber;
    String note;
    Set<Integer> authorsIds;
    Set<Integer> categoriesIds;
    Integer publisherId;
    Integer storageId;
}
