package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark,Long> {
//    List<Mark> getAllByFilm(Film film);
}
