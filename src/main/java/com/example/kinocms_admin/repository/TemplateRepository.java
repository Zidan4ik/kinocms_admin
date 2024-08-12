package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template,Long> {
    @Query(value = "SELECT * FROM templates ORDER BY id desc LIMIT 5", nativeQuery = true)
    List<Template> getTop5Templates();

}
