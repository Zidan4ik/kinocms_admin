package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Mark;
import com.example.kinocms_admin.entity.New;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MarkRepository extends JpaRepository<Mark,Long> {
    @Query("SELECT m FROM Mark m WHERE m.name=:markName")
    Optional<Mark> findByName(@Param("markName") String name);
    Set<Mark> getAllByFilms(List<Film> films);
    Set<Mark> getAllByCinemas(List<Cinema> cinemas);
    Set<Mark> getAllByNews(List<New> news);

}
