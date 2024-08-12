package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Template;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import com.example.kinocms_admin.repository.TemplateRepository;
import com.example.kinocms_admin.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class TemplateServiceImp implements TemplateService {
    private final TemplateRepository templateRepository;
    private final ImageServiceImp imageServiceImp;

    @Override
    public void save(Template template, MultipartFile file) {
        String fileNameHTML = null;
        if (file != null) {
            fileNameHTML = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            template.setNameFile(fileNameHTML);
        }
        templateRepository.save(template);
        try {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                imageServiceImp.saveFile(file, fileNameHTML, GalleriesType.templates, ImageType.file, template.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        templateRepository.deleteById(id);
    }

    @Override
    public List<Template> getAll() {
        return templateRepository.findAll();
    }

    @Override
    public List<Template> getFiveTemplates() {
        return templateRepository.getTop5Templates();
    }
}
