package com.kdudek.itemsapp.infrastructure.idempotency;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdempotencyRecord {

    @Id
    private UUID idempotencyKey;

    private String requestHash;

    private Integer responseStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> responseHeaders;

    private String jsonResponse;

    @CreationTimestamp
    private Instant createdAt;
}
