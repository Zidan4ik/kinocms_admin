package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Banner;
import com.example.kinocms_admin.entity.BannerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerImageRepository extends JpaRepository<BannerImage,Long> {
    List<BannerImage> getAllByBanner(Banner banner);
}
