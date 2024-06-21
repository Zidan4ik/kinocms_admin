package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
//    List<Genre> getAllByFilmList(List<Film> filmList);
    @Query("SELECT e FROM Genre e WHERE e.name = :genreName")
    Optional<Genre> findByName(@Param("genreName") String name);
}
