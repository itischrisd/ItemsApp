package com.kdudek.itemsapp.validation.validator;

import com.kdudek.itemsapp.validation.annotation.ExistsInDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExistsInDatabaseValidator implements ConstraintValidator<ExistsInDatabase, Object> {

    private final EntityManager entityManager;
    @SuppressWarnings("java:S3749")
    private Class<?> entityClass;

    @Override
    public void initialize(ExistsInDatabase constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Collection<?> idsToCheck = value instanceof Collection<?> ids ? ids : List.of(value);

        if (idsToCheck.isEmpty()) {
            return true;
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<?> root = query.from(entityClass);
        query.select(criteriaBuilder.count(root));
        query.where(root.get("id").in(idsToCheck));
        Long count = entityManager.createQuery(query).getSingleResult();
        return count == idsToCheck.stream().distinct().count();
    }
}
