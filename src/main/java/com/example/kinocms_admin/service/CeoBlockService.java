package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.enums.LanguageCode;

import java.util.Optional;

public interface CeoBlockService {
    void save(CeoBlock ceoBlock, Film film, LanguageCode code);
    void deleteById(long id);
    Optional<CeoBlock> getById(long id);
}
