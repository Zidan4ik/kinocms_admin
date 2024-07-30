package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.enums.PageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page,Long> {
    List<Page> getAllByType(PageType type);
}
