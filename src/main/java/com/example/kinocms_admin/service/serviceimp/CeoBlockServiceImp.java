package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.repository.CeoBlockRepository;
import com.example.kinocms_admin.service.CeoBlockService;
import jakarta.transaction.Transactional;
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
        hallBD.ifPresent(h -> ceoBlock.setId(h.getId()));
        ceoBlockRepository.save(ceoBlock);
    }

    @Override
    public void saveCinema(CeoBlock ceoBlock, Cinema cinema, LanguageCode code) {
        Optional<CeoBlock> cinemaBD = getByCinemaAndLanguageCode(cinema, code);
        cinemaBD.ifPresent(object -> ceoBlock.setId(object.getId()));
        ceoBlockRepository.save(ceoBlock);
    }

    @Override
    public void saveNew(CeoBlock ceoBlock, New newCinema, LanguageCode code) {
        Optional<CeoBlock> newBD = getByNewEntityAndLanguageCode(newCinema, code);
        newBD.ifPresent(object -> ceoBlock.setId(object.getId()));
        ceoBlockRepository.save(ceoBlock);
    }

    @Override
    public void saveShare(CeoBlock ceoBlock, Share share, LanguageCode code) {
        Optional<CeoBlock> shareBD = getByShareAndLanguageCode(share, code);
        shareBD.ifPresent(object -> ceoBlock.setId(object.getId()));
        ceoBlockRepository.save(ceoBlock);
    }

    @Override
    public void savePage(CeoBlock ceoBlock, Page page, LanguageCode code) {
        Optional<CeoBlock> shareBD = getByPageAndLanguageCode(page, code);
        shareBD.ifPresent(object -> ceoBlock.setId(object.getId()));
        ceoBlockRepository.save(ceoBlock);
    }

    @Override
    public void deleteById(long id) {
        ceoBlockRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByFilm(Film film) {
        ceoBlockRepository.deleteAllByFilm(film);
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

    @Override
    public Optional<CeoBlock> getByNewEntityAndLanguageCode(New newEntity, LanguageCode code) {
        return ceoBlockRepository.getByNewEntityAndLanguageCode(newEntity, code);
    }

    @Override
    public Optional<CeoBlock> getByShareAndLanguageCode(Share share, LanguageCode code) {
        return ceoBlockRepository.getByShareAndLanguageCode(share, code);
    }

    @Override
    public Optional<CeoBlock> getByPageAndLanguageCode(Page page, LanguageCode code) {
        return ceoBlockRepository.getByPageAndLanguageCode(page, code);
    }
}
