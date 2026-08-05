package com.kdudek.itemsapp.dto.response.common;

import java.time.Instant;

public interface VersionedResource {

    Integer getVersion();

    Instant getCreatedAt();

    Instant getUpdatedAt();
}
