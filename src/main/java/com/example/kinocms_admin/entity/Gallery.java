package com.example.kinocms_admin.entity;

import com.example.kinocms_admin.enums.GalleriesType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "galleries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String linkImage;
    private GalleriesType type;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    public Gallery(String linkImage, GalleriesType type, Film film) {
        this.linkImage = linkImage;
        this.type = type;
        this.film = film;
    }

    public Gallery(Long id, String linkImage, GalleriesType type) {
        this.id = id;
        this.linkImage = linkImage;
        this.type = type;
    }
}
