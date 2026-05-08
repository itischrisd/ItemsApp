package com.kdudek.itemsapp.dto.request.publisher;

import com.kdudek.itemsapp.common.DomainLimits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class PublisherCreateDTO {

    @NotBlank
    @Size(max = DomainLimits.LONG_NAME)
    String name;
}
