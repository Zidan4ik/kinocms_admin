package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
    List<Genre> getAllByFilm(Film film);
}
