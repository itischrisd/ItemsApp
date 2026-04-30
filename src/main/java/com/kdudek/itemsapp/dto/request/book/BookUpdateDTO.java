package com.kdudek.itemsapp.dto.request.book;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Year;

@Value
@Builder
@Jacksonized
public class BookUpdateDTO {

    String title;
    Year yearOfPublication;
    String coverType;
    String serialNumber;
    Integer editionNumber;
    String catalogNumber;
    String note;
}
