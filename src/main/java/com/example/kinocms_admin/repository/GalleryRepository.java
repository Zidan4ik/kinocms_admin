package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery,Long> {
    List<Gallery> getAllByFilm(Film film);
    List<Gallery> getAllByCinema(Cinema film);
    List<Gallery> getAllByHall(Hall hall);
}
