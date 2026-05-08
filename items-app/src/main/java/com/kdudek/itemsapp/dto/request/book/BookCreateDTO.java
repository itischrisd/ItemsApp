package com.kdudek.itemsapp.dto.request.book;

import com.kdudek.itemsapp.entity.Category;
import com.kdudek.itemsapp.entity.Storage;
import com.kdudek.itemsapp.entity.book.Author;
import com.kdudek.itemsapp.entity.book.Publisher;
import com.kdudek.itemsapp.common.DomainLimits;
import com.kdudek.itemsapp.validation.annotation.ExistsInDatabase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Range;

import java.time.Year;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class BookCreateDTO {

    @NotBlank
    @Size(max = DomainLimits.LONG_NAME)
    String title;
    @Range(min = 1000, max = 3000)
    Year yearOfPublication;
    @Size(max = DomainLimits.SHORT_NAME)
    String coverType;
    @Size(max = DomainLimits.CODE)
    String serialNumber;
    @Positive
    Integer editionNumber;
    @Size(max = DomainLimits.CODE)
    String catalogNumber;
    @Size(max = DomainLimits.DESCRIPTION)
    String note;
    @ExistsInDatabase(entity = Author.class)
    Set<Integer> authorsIds;
    @ExistsInDatabase(entity = Category.class)
    Set<Integer> categoriesIds;
    @ExistsInDatabase(entity = Publisher.class)
    Integer publisherId;
    @ExistsInDatabase(entity = Storage.class)
    Integer storageId;
}
