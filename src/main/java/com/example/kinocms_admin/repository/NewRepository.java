package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.New;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewRepository extends JpaRepository<New, Long> {
}
