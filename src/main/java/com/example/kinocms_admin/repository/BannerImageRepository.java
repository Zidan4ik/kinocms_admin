package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.BannerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerImageRepository extends JpaRepository<BannerImage,Long> {
}
