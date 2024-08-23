package com.example.kinocms_admin.service.serviceimp;


import com.example.kinocms_admin.entity.Banner;
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
    private final ImageServiceImp imageServiceImp;

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
        if (file != null && !Objects.equals(file.getOriginalFilename(), "exist")) { //не генеруємо нову назву, якщо 'exist'
            fileName = imageServiceImp.generateFileName(file);
            bannerImage.setNameImage(fileName);
        }
        save(bannerImage);
        try {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()
                    && !Objects.equals(file.getOriginalFilename(), "exist")) {
                uploadDir = "./uploads/banner/" + bannerImage.getId();
                ImageUtil.saveAfterDelete(uploadDir, file, fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFiles(List<BannerImage> bannersImages, List<MultipartFile> files) {
        if (files != null) {
            int length = (bannersImages.size() == files.size()) ? bannersImages.size() : -1;
            for (int i = 0; i < length; i++) {
                saveFile(bannersImages.get(i), files.get(i));
            }
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
    public List<BannerImage> getAllByBanner(Banner banner) {
        return bannerImageRepository.getAllByBanner(banner);
    }

    @Override
    public Optional<BannerImage> getById(Long id) {
        return bannerImageRepository.findById(id);
    }
}
