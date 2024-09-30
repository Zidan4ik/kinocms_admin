package com.example.kinocms_admin.service.serviceimp;

import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenericSearchSpecificationTest {

    @Mock
    private Root<Object> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private Path<Object> path;

    @Mock
    private Expression<String> expression;

    @Mock
    private Predicate predicate;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldToPredicate_WithStringColumn() {
        List<String> columns = List.of("name");
        String search = "test";
        GenericSearchSpecification<Object> specification = new GenericSearchSpecification<>(columns, search);
        when(root.get("name")).thenReturn(path);
        when(path.getJavaType()).thenAnswer(invocation -> String.class);
        when(criteriaBuilder.upper(any(Expression.class))).thenReturn(expression);
        when(criteriaBuilder.like(any(Expression.class), anyString())).thenReturn(predicate);
        specification.toPredicate(root, query, criteriaBuilder);
        verify(criteriaBuilder, times(1)).like(any(Expression.class), anyString());
    }

    @Test
    void shouldToPredicate_WithLocalDateColumn() {
        List<String> columns = List.of("createdDate");
        String search = "2023-09-30";
        GenericSearchSpecification<Object> specification = new GenericSearchSpecification<>(columns, search);
        when(root.get("createdDate")).thenReturn(path);
        when(path.getJavaType()).thenAnswer(invocation -> LocalDate.class);
        when(criteriaBuilder.function("DATE_FORMAT", String.class, path, null))
                .thenReturn(expression);
        when(criteriaBuilder.like(any(Expression.class), anyString())).thenReturn(predicate);
        specification.toPredicate(root, query, criteriaBuilder);
        verify(criteriaBuilder, times(1)).like(any(Expression.class), anyString());
    }

    @Test
    void shouldToPredicate_WithLongColumn() {
        List<String> columns = Arrays.asList("id");
        String search = "123";
        GenericSearchSpecification<Object> specification = new GenericSearchSpecification<>(columns, search);
        when(root.get("id")).thenReturn(path);
        when(path.getJavaType()).thenAnswer(invocation -> Long.class);
        when(criteriaBuilder.toString(any(Expression.class))).thenReturn(expression);
        when(criteriaBuilder.like(any(Expression.class), anyString())).thenReturn(predicate);
        specification.toPredicate(root, query, criteriaBuilder);
        verify(criteriaBuilder, times(1)).like(any(Expression.class), anyString());
    }

    @Test
    void shouldBuildExpression_WithSimpleColumn() {
        Path<Object> path = mock(Path.class);
        GenericSearchSpecification<Object> specification = new GenericSearchSpecification<>(List.of("name"), "search");
        when(path.get("name")).thenReturn(path);
        Expression<String> result = specification.buildExpression("name", path);
        assertNotNull(result, "Result should not be null");
        verify(path, times(1)).get("name");
    }
    @Test
    void shouldToPredicate_WithFalseReturnInIfOperation() {
        List<String> columns = Arrays.asList("id");
        String search = "123";
        GenericSearchSpecification<Object> specification = new GenericSearchSpecification<>(columns, search);
        when(root.get("id")).thenReturn(path);
        when(path.getJavaType()).thenAnswer(invocation -> null);
        specification.toPredicate(root, query, criteriaBuilder);
    }
    @Test
    void shouldToPredicate_WhenSearchValueIsNull() {
        GenericSearchSpecification<Object> specification = new GenericSearchSpecification<>(null, null);
        specification.toPredicate(root, query, criteriaBuilder);
    }

    @Test
    void testBuildExpression_withDotInColumn() {
        Path<Object> path = mock(Path.class);
        Path<Object> nestedPath = mock(Path.class);
        GenericSearchSpecification<Object> specification = new GenericSearchSpecification<>(List.of("nested.field"), "search");
        when(path.get("nested")).thenReturn(nestedPath);
        when(nestedPath.get("field")).thenReturn(nestedPath);
        specification.buildExpression("nested.field", path);
        verify(path, times(1)).get("nested");
        verify(nestedPath, times(1)).get("field");
    }
}