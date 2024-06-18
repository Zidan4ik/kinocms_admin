package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.CeoBlock;

import java.util.Optional;

public interface CeoBlockService {
    void save(CeoBlock ceoBlock);
    void deleteById(long id);
    Optional<CeoBlock> getById(long id);
}
