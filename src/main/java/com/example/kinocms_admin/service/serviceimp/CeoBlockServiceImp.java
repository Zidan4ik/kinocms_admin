package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.repository.CeoBlockRepository;
import com.example.kinocms_admin.service.CeoBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CeoBlockServiceImp implements CeoBlockService {
    private final CeoBlockRepository ceoBlockRepository;

    @Override
    public void saveFilm(CeoBlock ceoBlock, Film film, LanguageCode code) {
        Optional<CeoBlock> ceoBlockBD = getByFilmAndLanguageBlock(film, code);
        ceoBlockBD.ifPresent(block -> ceoBlock.setId(block.getId()));
        ceoBlockRepository.save(ceoBlock);
    }

    @Override
    public void saveHall(CeoBlock ceoBlock, Hall hall, LanguageCode code) {
        Optional<CeoBlock> hallBD = getByHallAndLanguageCode(hall, code);
        hallBD.ifPresent(object -> ceoBlock.setId(object.getId()));
        ceoBlockRepository.save(ceoBlock);
    }
    @Override
    public void saveCinema(CeoBlock ceoBlock, Cinema cinema, LanguageCode code) {
        Optional<CeoBlock> cinemaBD = getByCinemaAndLanguageCode(cinema, code);
        cinemaBD.ifPresent(object -> ceoBlock.setId(object.getId()));
        ceoBlockRepository.save(ceoBlock);
    }

    @Override
    public void deleteById(long id) {
        ceoBlockRepository.deleteById(id);
    }

    @Override
    public Optional<CeoBlock> getById(long id) {
        return ceoBlockRepository.findById(id);
    }

    @Override
    public Optional<CeoBlock> getByFilmAndLanguageBlock(Film film, LanguageCode code) {
        return ceoBlockRepository.getByFilmAndLanguageCode(film, code);
    }

    @Override
    public Optional<CeoBlock> getByCinemaAndLanguageCode(Cinema cinema, LanguageCode code) {
        return ceoBlockRepository.getByCinemaAndLanguageCode(cinema, code);
    }

    @Override
    public Optional<CeoBlock> getByHallAndLanguageCode(Hall hall, LanguageCode code) {
        return ceoBlockRepository.getByHallAndLanguageCode(hall, code);
    }
}
