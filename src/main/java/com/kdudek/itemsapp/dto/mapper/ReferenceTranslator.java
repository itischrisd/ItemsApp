package com.kdudek.itemsapp.dto.mapper;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReferenceTranslator {

    private final EntityManager entityManager;

    @Named("idToReference")
    public <T> T resolve(Long id, @TargetType Class<T> entityClass) {
        if (id == null) {
            return null;
        }
        return entityManager.getReference(entityClass, id);
    }
}
