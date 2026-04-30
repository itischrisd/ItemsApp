package com.kdudek.itemsapp.dto.response.book;

import com.kdudek.itemsapp.dto.response.author.AuthorSummaryDTO;
import com.kdudek.itemsapp.dto.response.category.CategorySummaryDTO;
import com.kdudek.itemsapp.dto.response.publisher.PublisherSummaryDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageSummaryDTO;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Year;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class BookDetailsDTO {

    Long id;
    String title;
    Year yearOfPublication;
    String coverType;
    String serialNumber;
    Integer editionNumber;
    String catalogNumber;
    String note;
    Set<AuthorSummaryDTO> authors;
    Set<CategorySummaryDTO> categories;
    PublisherSummaryDTO publisher;
    StorageSummaryDTO storage;
}
