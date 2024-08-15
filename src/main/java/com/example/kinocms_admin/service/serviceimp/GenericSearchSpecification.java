package com.example.kinocms_admin.service.serviceimp;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GenericSearchSpecification<T> implements Specification<T> {
    private final List<String> columns;
    private final String search;

    public GenericSearchSpecification(List<String> columns, String search) {
        this.columns = columns;
        this.search = search;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (search != null) {
            for (String column : columns) {
                Path<Object> path = root.get(column);
                Class<?> type = path.getJavaType();
                if (type == String.class) {
                    Expression<String> expression = buildExpression(column, root);
                    Predicate predicate = criteriaBuilder.like(criteriaBuilder.upper(expression), "%" + search.toUpperCase() + "%");
                    predicates.add(predicate);
                } else if (type == LocalDate.class) {
                    Expression<String> dateStringExpr = criteriaBuilder.function("DATE_FORMAT", String.class, path, criteriaBuilder.literal("%Y-%m-%d"));
                    Predicate predicate = criteriaBuilder.like(dateStringExpr, "%" + search + "%");
                    predicates.add(predicate);
                } else if (type == Long.class) {
                    Predicate predicate = criteriaBuilder.like(criteriaBuilder.toString(root.get("id")), "%" + search + "%");
                    predicates.add(predicate);
                }
            }
        }
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    private Expression<String> buildExpression(String column, Path<T> path) {
        if (!columns.contains("."))
            return path.get(column);
        String[] parts = column.split("\\.");
        int i = 0;
        for (; i < parts.length - 1; i++) {
            path = path.get(parts[i]);
        }

        return path.get(parts[i]);
    }
}
