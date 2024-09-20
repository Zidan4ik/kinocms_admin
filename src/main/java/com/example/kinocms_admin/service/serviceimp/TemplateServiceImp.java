package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Template;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import com.example.kinocms_admin.model.TemplateDTO;
import com.example.kinocms_admin.repository.TemplateRepository;
import com.example.kinocms_admin.service.TemplateService;
import com.example.kinocms_admin.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateServiceImp implements TemplateService {
    private final TemplateRepository templateRepository;
    private final ImageServiceImp imageServiceImp;

    @Override
    public void save(Template template, MultipartFile file) {
        LogUtil.logSaveNotification("template", "id", template.getId());
        String fileNameHTML = null;
        if (file != null) {
            fileNameHTML = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            template.setNameFile(fileNameHTML);
        }
        templateRepository.save(template);
        LogUtil.logSaveInfo("Template", "id", template.getId());
        try {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                imageServiceImp.saveFile(file, fileNameHTML, GalleriesType.templates, ImageType.file, template.getId());
            }
        } catch (IOException e) {
            log.error("Error saving file: {} for template with id: {}", fileNameHTML, template.getId(), e);
        }
    }

    @Override
    public void delete(Long id) {
        LogUtil.logDeleteNotification("template", "id", id);
        templateRepository.deleteById(id);
        LogUtil.logDeleteInfo("Template", "id", id);
    }

    @Override
    public List<Template> getAll() {
        LogUtil.logGetAllNotification("templates");
        List<Template> templates = templateRepository.findAll();
        LogUtil.logSizeInfo("templates", templates.size());
        return templates;
    }

    @Override
    public List<Template> getFiveTemplates() {
        log.info("Fetch 5 templates");
        List<Template> templatesFive = templateRepository.getTop5Templates();
        LogUtil.logSizeInfo("templates", templatesFive.size());
        return templatesFive;
    }

    @Override
    public Optional<Template> getById(Long id) {
        LogUtil.logGetNotification("template", "id", id);
        Optional<Template> templateId = templateRepository.findById(id);
        LogUtil.logGetInfo("Template", "id", id, templateId.isPresent());
        return templateId;

    }

    public String getPathToFile(TemplateDTO dto) {
        if (dto.getId() != 0) {
            Optional<Template> templateById = getById(dto.getId());
            if (templateById.isPresent()) {
                Template template = templateById.get();
                return "./uploads/templates/file/" + template.getId() + "/" + template.getNameFile();
            }
        } else {
            Template entity = new Template();
            save(entity, dto.getFileHtml());
            Optional<Template> templateById = getById(entity.getId());
            if (templateById.isPresent()) {
                Template template = templateById.get();
                return "./uploads/templates/file/" + template.getId() + "/" + template.getNameFile();
            }
        }
        return null;
    }
}
