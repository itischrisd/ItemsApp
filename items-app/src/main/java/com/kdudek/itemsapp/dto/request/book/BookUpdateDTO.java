package com.kdudek.itemsapp.dto.request.book;

import com.kdudek.itemsapp.common.DomainLimits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Range;

import java.time.Year;

@Value
@Builder
@Jacksonized
public class BookUpdateDTO {

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
}
