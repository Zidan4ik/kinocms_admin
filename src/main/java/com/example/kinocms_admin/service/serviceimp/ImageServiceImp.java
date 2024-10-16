package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import com.example.kinocms_admin.service.GalleryOwner;
import com.example.kinocms_admin.service.ImageService;
import com.example.kinocms_admin.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
@Slf4j
public class ImageServiceImp implements ImageService {
    @Override
    public void saveFile(MultipartFile file, String nameLogo, GalleriesType type, ImageType imageType, Long id) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            log.info("Saving {} with id: {} in directory", file.getOriginalFilename(), id);
            String uploadDir = "/home/slj/projects/KinoCMS-R.Pravnyk/uploads/" + type.toString() + "/" + imageType.toString() + "/" + id;
            ImageUtil.saveAfterDelete(uploadDir, file, nameLogo);
            log.info("File: {} was successfully saved in directory ({})", file.getOriginalFilename(), uploadDir);
        } else {
            log.warn("File with id: {} wasn't saved in directory", id);
        }
    }

    @Override
    public void saveFiles(HashMap<String, MultipartFile> mapFiles, GalleriesType type, Long id) throws IOException {
        log.info("Saving files in directory");
        String uploadDir = "/home/slj/projects/KinoCMS-R.Pravnyk/uploads/" + type.toString() + "/galleries/" + id;
        ImageUtil.savesAfterDelete(uploadDir, mapFiles);
        log.info("Files were successfully saved in directory");
    }

    @Override
    public String generateFileName(MultipartFile file) {
        log.info("Generating name for file");
        String name = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        log.info("Name: {} was successfully generated", name);
        return name;
    }

    @Override
    public List<Gallery> createGallery(HashMap<String, MultipartFile> files, GalleryOwner owner, GalleriesType type) {
        log.info("Generating gallery with {} more files", files.size());
        List<Gallery> galleriesRes = new ArrayList<>();
        for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
            if (entry.getValue() != null) {
                galleriesRes.add(new Gallery(entry.getKey(), type, owner));
            }
        }
        log.info("Gallery was regenerating with {} more files", files.size());
        return galleriesRes;
    }

    @Override
    public HashMap<String, MultipartFile> generateFilesMap(List<MultipartFile> files) {
        HashMap<String, MultipartFile> result = new HashMap<>();
        if (files != null) {
            for (MultipartFile file : files) {
                if (file != null) {
                    String name = generateFileName(file);
                    result.put(name, file);
                }
            }
        }
        return result;
    }

    @Override
    public void deleteFiles(GalleriesType type, Long id) {
        log.info("Deleting files with type: {} & id: {}", type, id);
        ImageUtil.deleteFile(type, id);
        log.info("Deleting files were successfully deleted");
    }
}
