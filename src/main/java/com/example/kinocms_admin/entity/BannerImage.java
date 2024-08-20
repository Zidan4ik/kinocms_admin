package com.example.kinocms_admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "banners_images")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BannerImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameImage;
    @Lob
    @Column(columnDefinition = "text")
    private String text;
    @Column(length = 2048)
    private String url;
    @ManyToOne
    private Banner banner;
}
