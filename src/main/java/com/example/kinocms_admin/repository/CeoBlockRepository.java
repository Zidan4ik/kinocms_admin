package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.CeoBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CeoBlockRepository extends JpaRepository<CeoBlock,Long> {
}
