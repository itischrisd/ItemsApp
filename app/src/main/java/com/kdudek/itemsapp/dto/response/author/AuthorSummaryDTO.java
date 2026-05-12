package com.kdudek.itemsapp.dto.response.author;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AuthorSummaryDTO {

    Long id;
    String name;
    String surname;
}
