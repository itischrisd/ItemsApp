package com.kdudek.itemsapp.dto.request.author;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AuthorCreateDTO {

    String name;
    String surname;
}
