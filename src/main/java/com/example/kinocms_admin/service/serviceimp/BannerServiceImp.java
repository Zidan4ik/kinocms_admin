package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Banner;
import com.example.kinocms_admin.repository.BannerRepository;
import com.example.kinocms_admin.service.BannerService;
import com.example.kinocms_admin.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerServiceImp implements BannerService {
    private final BannerRepository bannerRepository;

    @Override
    public void save(Banner banner) {
        LogUtil.logSaveNotification("banner", "id", banner.getId());
        bannerRepository.save(banner);
        LogUtil.logSaveInfo("Banner", "id", banner.getId());
    }

    @Override
    public void deleteById(Long id) {
        LogUtil.logDeleteNotification("banner", "id", id);
        bannerRepository.deleteById(id);
        LogUtil.logDeleteInfo("Banner", "id", id);
    }

    @Override
    public Optional<Banner> getById(Long id) {
        LogUtil.logGetNotification("banner", "id", id);
        Optional<Banner> bannerRepositoryById = bannerRepository.findById(id);
        LogUtil.logGetInfo("Banner", "id", id, bannerRepositoryById.isPresent());
        return bannerRepositoryById;
    }

    @Override
    public List<Banner> getAll() {
        LogUtil.logGetAllNotification("banners");
        List<Banner> banners = bannerRepository.findAll();
        LogUtil.logSizeInfo("banners", banners.size());
        return banners;
    }
}
