package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Banner;
import com.example.kinocms_admin.repository.BannerRepository;
import com.example.kinocms_admin.service.BannerService;
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
        bannerRepository.save(banner);
    }

    @Override
    public void delete(Long id) {
        bannerRepository.deleteById(id);
    }

    @Override
    public Optional<Banner> getById(Long id) {
        return bannerRepository.findById(id);
    }

    @Override
    public List<Banner> getAll() {
        return bannerRepository.findAll();
    }
}
