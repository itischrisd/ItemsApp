package com.kdudek.itemsapp.dto.response.book;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class BookSummaryDTO {

    Long id;
    String title;
    Integer yearOfPublication;
    String coverType;
    String serialNumber;
    Integer editionNumber;
    String catalogNumber;
    String note;
}
