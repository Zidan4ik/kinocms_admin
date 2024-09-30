package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ImageService {
    void saveFile(MultipartFile file, String nameLogo, GalleriesType type, ImageType typeImage, Long id) throws IOException;

    void saveFiles(HashMap<String, MultipartFile> mapFiles, GalleriesType type, Long id) throws IOException;

    String generateFileName(MultipartFile file);

    List<Gallery> createGallery(HashMap<String,MultipartFile> files, GalleryOwner owner, GalleriesType type);

    void deleteFiles(GalleriesType type, Long id);

    Map<String, MultipartFile> generateFilesMap(List<MultipartFile> files);
}
