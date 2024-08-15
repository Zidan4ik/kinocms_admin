package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Template;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import com.example.kinocms_admin.model.TemplateDTO;
import com.example.kinocms_admin.repository.TemplateRepository;
import com.example.kinocms_admin.service.TemplateService;
import com.example.kinocms_admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


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

    @Override
    public Optional<Template> getById(Long id) {
        return templateRepository.findById(id);
    }

    public String getPathToFile(TemplateDTO dto){
        if(dto.getId()!=0){
            Optional<Template> templateById = getById(dto.getId());
            if(templateById.isPresent()){
                Template template = templateById.get();
                return "./uploads/templates/file/"+template.getId()+"/"+template.getNameFile();
            }
        }else{
            Template entity = new Template();
            save(entity,dto.getFileHtml());
            Optional<Template> templateById = getById(entity.getId());
            if(templateById.isPresent()){
                Template template = templateById.get();
                return "./uploads/templates/file/"+template.getId()+"/"+template.getNameFile();
            };
        }
        return null;
    }
    public void deleteFile(GalleriesType type, Long id){
        Path path = Paths.get("./uploads/"+type.toString());
        try {
            ImageUtil.deleteFoldersByName(path,String.valueOf(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
