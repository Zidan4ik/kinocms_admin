package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Template;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TemplateService {
    void save(Template template,MultipartFile file);
    void deleteById(Long id);
    List<Template> getAll();
    List<Template> getFiveTemplates();
    Optional<Template> getById(Long id);
}
