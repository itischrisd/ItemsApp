package com.kdudek.itemsapp.dto.response.common;

import java.time.LocalDateTime;

public interface VersionedResource {

    Integer getVersion();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
