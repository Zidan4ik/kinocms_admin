package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film,Long> {
}
