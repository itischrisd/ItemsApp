package com.kdudek.itemsapp.dto.request.category;

import com.kdudek.itemsapp.common.DomainLimits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryUpdateDTO {

    @NotBlank
    @Size(max = DomainLimits.SHORT_NAME)
    String name;
}
