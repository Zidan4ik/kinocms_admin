package com.example.kinocms_admin.service.serviceimp;


import com.example.kinocms_admin.entity.Banner;
import com.example.kinocms_admin.entity.BannerImage;
import com.example.kinocms_admin.repository.BannerImageRepository;
import com.example.kinocms_admin.service.BannerImageService;
import com.example.kinocms_admin.util.ImageUtil;
import com.example.kinocms_admin.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerImageServiceImp implements BannerImageService {
    private final BannerImageRepository bannerImageRepository;
    private final ImageServiceImp imageServiceImp;

    @Override
    public void save(BannerImage bannerImage) {
        LogUtil.logSaveNotification("bannerImage", "id", bannerImage.getId());
        bannerImageRepository.save(bannerImage);
        LogUtil.logSaveInfo("bannerImage", "id", bannerImage.getId());
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
                uploadDir = "/home/slj/projects/KinoCMS-R.Pravnyk/uploads/banner/" + bannerImage.getId();
                ImageUtil.saveAfterDelete(uploadDir, file, fileName);
            }
        } catch (IOException e) {
            LogUtil.logErrorSavingFiles(e);
        }
    }

    @Override
    public void saveFiles(List<BannerImage> bannersImages, List<MultipartFile> files) {
        LogUtil.logSaveAllInfo("bannersImages");
        if (files != null) {
            int length = (bannersImages.size() == files.size()) ? bannersImages.size() : -1;
            for (int i = 0; i < length; i++) {
                saveFile(bannersImages.get(i), files.get(i));
            }
        }
        LogUtil.logSaveAllInfo("bannersImages");
    }

    @Override
    public void deleteById(Long id) {
        LogUtil.logDeleteNotification("bannerImage", "id", id);
        bannerImageRepository.deleteById(id);
        LogUtil.logDeleteInfo("bannerImage", "id", id);
    }

    @Override
    public List<BannerImage> getAll() {
        LogUtil.logGetAllNotification("bannersImages");
        List<BannerImage> bannersImages = bannerImageRepository.findAll();
        LogUtil.logSizeInfo("bannersImages", bannersImages.size());
        return bannersImages;
    }

    @Override
    public List<BannerImage> getAllByBanner(Banner banner) {
        LogUtil.logGetAllNotification("bannersImages", "banner", banner);
        List<BannerImage> bannerImageByBanner = bannerImageRepository.getAllByBanner(banner);
        LogUtil.logSizeInfo("bannersImages by banner", bannerImageByBanner.size());
        return bannerImageByBanner;
    }

    @Override
    public Optional<BannerImage> getById(Long id) {
        LogUtil.logGetNotification("bannerImage", "id", id);
        Optional<BannerImage> bannerImageById = bannerImageRepository.findById(id);
        LogUtil.logGetInfo("bannerImage", "id", id, bannerImageById.isPresent());
        return bannerImageById;
    }
}
