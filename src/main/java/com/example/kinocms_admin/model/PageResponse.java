package com.example.kinocms_admin.model;

import lombok.Value;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
@Value
public class PageResponse<T> {
    List<T> content;
    Metadata metadata;

    public static <T> PageResponse<T> of(Page<T> page) {
        var metadata = new Metadata(page.getNumber(), page.getSize(), page.getTotalElements(),page.getTotalPages());
        return new PageResponse<>(page.getContent(), metadata);
    }

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalElements;
        long totalPages;
    }
}
