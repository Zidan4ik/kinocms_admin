package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall,Long> {
    List<Hall> getAllByCinema(Cinema cinema);
    void deleteAllByCinema(Cinema cinema);
}
