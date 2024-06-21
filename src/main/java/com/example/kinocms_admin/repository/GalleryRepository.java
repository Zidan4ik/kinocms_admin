package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery,Long> {
    List<Gallery> findAllByFilm(Film film);
}
