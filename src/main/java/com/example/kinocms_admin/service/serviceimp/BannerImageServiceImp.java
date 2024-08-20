package com.example.kinocms_admin.service.serviceimp;


import com.example.kinocms_admin.entity.BannerImage;
import com.example.kinocms_admin.repository.BannerImageRepository;
import com.example.kinocms_admin.service.BannerImageService;
import com.example.kinocms_admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BannerImageServiceImp implements BannerImageService {
    private final BannerImageRepository bannerImageRepository;
    @Override
    public void save(BannerImage bannerImage) {
        bannerImageRepository.save(bannerImage);
    }

    @Override
    public void saveFile(BannerImage bannerImage, MultipartFile file) {
        String uploadDir;
        String fileName = null;

        if (bannerImage.getId() != null) {
            Optional<BannerImage> banner = getById(bannerImage.getId());
            banner.ifPresent((object) -> bannerImage.setNameImage(object.getNameImage()));
        }
        if (file != null) {
            fileName = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            bannerImage.setNameImage(fileName);
        }
        save(bannerImage);
        try {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/banner/" + bannerImage.getId();
                ImageUtil.saveAfterDelete(uploadDir, file, fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        bannerImageRepository.deleteById(id);
    }

    @Override
    public List<BannerImage> getAll() {
        return bannerImageRepository.findAll();
    }

    @Override
    public Optional<BannerImage> getById(Long id) {
        return bannerImageRepository.findById(id);
    }
}
