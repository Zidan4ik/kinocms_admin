package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import com.example.kinocms_admin.service.GalleryOwner;
import com.example.kinocms_admin.service.ImageService;
import com.example.kinocms_admin.util.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@Service
public class ImageServiceImp implements ImageService {
    @Override
    public void saveFile(MultipartFile file, String nameLogo, GalleriesType type, ImageType imageType, Long id) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uploadDir = "./uploads/" + type.toString() + "/" + imageType.toString() + "/" + id;
            ImageUtil.saveAfterDelete(uploadDir, file, nameLogo);
        }
    }

    @Override
    public void saveFiles(List<MultipartFile> files, HashMap<String, MultipartFile> mapFiles, GalleriesType type, Long id) throws IOException {
        String uploadDir = "./uploads/" + type.toString() + "/galleries/" + id;
        ImageUtil.savesAfterDelete(uploadDir, mapFiles);
    }

    @Override
    public String generateFileName(MultipartFile file) {
        return UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    }

    @Override
    public List<Gallery> createGallery(List<MultipartFile> files, GalleryOwner owner, GalleriesType type, Map<String, MultipartFile> mapFile) {
        List<Gallery> galleriesRes = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                if (file != null) {
                    String name = generateFileName(file);
                    galleriesRes.add(new Gallery(name, type, owner));
                    mapFile.put(name, file);
                }
            }
        }
        return galleriesRes;
    }

    @Override
    public void deleteFiles(GalleriesType type, Long id) {
        Path path = Paths.get("./uploads/"+type.toString());
        try {
            ImageUtil.deleteFoldersByName(path,String.valueOf(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
