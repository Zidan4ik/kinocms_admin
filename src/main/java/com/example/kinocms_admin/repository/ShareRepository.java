package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share,Long> {
}
