package com.kdudek.itemsapp.dto.request.item;

import com.kdudek.itemsapp.common.DomainLimits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ItemUpdateDTO {

    @NotBlank
    @Size(max = DomainLimits.LONG_NAME)
    String description;
    @Size(max = DomainLimits.SHORT_TEXT)
    String note;
}
