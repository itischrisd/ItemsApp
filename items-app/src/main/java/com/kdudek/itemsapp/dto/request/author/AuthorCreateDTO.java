package com.kdudek.itemsapp.dto.request.author;

import com.kdudek.itemsapp.common.DomainLimits;
import com.kdudek.itemsapp.validation.annotation.NullableNotBlank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AuthorCreateDTO {

    @NotBlank
    @Size(max = DomainLimits.SHORT_NAME)
    String name;
    @NullableNotBlank
    @Size(max = DomainLimits.SHORT_NAME)
    String surname;
}
