package com.example.kinocms_admin.entity;

import com.example.kinocms_admin.enums.BannerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "banners")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rotationSpeed;
    private boolean status;
    private BannerType type;
    @OneToMany(mappedBy = "banner")
    private List<BannerImage> bannerImage;
}
