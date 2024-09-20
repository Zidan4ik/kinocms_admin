package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.repository.CeoBlockRepository;
import com.example.kinocms_admin.service.CeoBlockService;
import com.example.kinocms_admin.util.LogUtil;
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
        LogUtil.logSaveNotification("ceoBlock", "film", "code", film, code, ceoBlock.getId());
        Optional<CeoBlock> ceoBlockBD = getByFilmAndLanguageCode(film, code);
        ceoBlockBD.ifPresent(block -> ceoBlock.setId(block.getId()));
        ceoBlockRepository.save(ceoBlock);
        LogUtil.logSaveInfo("CeoBlock", ceoBlock.getId());
    }

    @Override
    public void saveHall(CeoBlock ceoBlock, Hall hall, LanguageCode code) {
        LogUtil.logSaveNotification("ceoBlock", "hall", "code", hall, code, ceoBlock.getId());
        Optional<CeoBlock> hallBD = getByHallAndLanguageCode(hall, code);
        hallBD.ifPresent(h -> ceoBlock.setId(h.getId()));
        ceoBlockRepository.save(ceoBlock);
        LogUtil.logSaveInfo("CeoBlock", ceoBlock.getId());
    }

    @Override
    public void saveCinema(CeoBlock ceoBlock, Cinema cinema, LanguageCode code) {
        LogUtil.logSaveNotification("ceoBlock", "cinema", "code", cinema, code, ceoBlock.getId());
        Optional<CeoBlock> cinemaBD = getByCinemaAndLanguageCode(cinema, code);
        cinemaBD.ifPresent(object -> ceoBlock.setId(object.getId()));
        ceoBlockRepository.save(ceoBlock);
        LogUtil.logSaveInfo("CeoBlock", ceoBlock.getId());
    }

    @Override
    public void saveNew(CeoBlock ceoBlock, New newCinema, LanguageCode code) {
        LogUtil.logSaveNotification("ceoBlock", "new", "code", newCinema, code, ceoBlock.getId());
        Optional<CeoBlock> newBD = getByNewEntityAndLanguageCode(newCinema, code);
        newBD.ifPresent(object -> ceoBlock.setId(object.getId()));
        ceoBlockRepository.save(ceoBlock);
        LogUtil.logSaveInfo("CeoBlock", ceoBlock.getId());
    }

    @Override
    public void saveShare(CeoBlock ceoBlock, Share share, LanguageCode code) {
        LogUtil.logSaveNotification("ceoBlock", "share", "code", share, code, ceoBlock.getId());
        Optional<CeoBlock> shareBD = getByShareAndLanguageCode(share, code);
        shareBD.ifPresent(object -> ceoBlock.setId(object.getId()));
        ceoBlockRepository.save(ceoBlock);
        LogUtil.logSaveInfo("CeoBlock", ceoBlock.getId());
    }

    @Override
    public void savePage(CeoBlock ceoBlock, Page page, LanguageCode code) {
        LogUtil.logSaveNotification("ceoBlock", "page", "code", page, code, ceoBlock.getId());
        Optional<CeoBlock> shareBD = getByPageAndLanguageCode(page, code);
        shareBD.ifPresent(object -> ceoBlock.setId(object.getId()));
        ceoBlockRepository.save(ceoBlock);
        LogUtil.logSaveInfo("CeoBlock", ceoBlock.getId());
    }

    @Override
    public void deleteById(long id) {
        LogUtil.logDeleteNotification("ceoBlock", "id", id);
        ceoBlockRepository.deleteById(id);
        LogUtil.logDeleteInfo("CeoBlock", "id", id);
    }

    @Override
    @Transactional
    public void deleteAllByFilm(Film film) {
        LogUtil.logDeleteAllNotification("ceoBlocks", "film", film);
        ceoBlockRepository.deleteAllByFilm(film);
        LogUtil.logDeleteAllInfo("ceoBlocks", "film");
    }

    @Override
    public Optional<CeoBlock> getById(long id) {
        LogUtil.logGetNotification("ceoBlock", "id", id);
        Optional<CeoBlock> ceoBlockById = ceoBlockRepository.findById(id);
        LogUtil.logGetInfo("CeoBlock", "id", id, ceoBlockById.isPresent());
        return ceoBlockById;
    }

    public Optional<CeoBlock> getByFilmAndLanguageCode(Film film, LanguageCode code) {
        LogUtil.logGetNotification("ceoBlock", "film", "code", film, code);
        Optional<CeoBlock> ceoBlockByFilmAndLanguageCode = ceoBlockRepository.getByFilmAndLanguageCode(film, code);
        LogUtil.logGetInfo("ceoBlock", "film", "code", film, code, ceoBlockByFilmAndLanguageCode.isPresent());
        return ceoBlockByFilmAndLanguageCode;
    }

    @Override
    public Optional<CeoBlock> getByCinemaAndLanguageCode(Cinema cinema, LanguageCode code) {
        LogUtil.logGetNotification("ceoBlock", "cinema", "code", cinema, code);
        Optional<CeoBlock> ceoBlockByCinemaAndLanguageCode = ceoBlockRepository.getByCinemaAndLanguageCode(cinema, code);
        LogUtil.logGetInfo("ceoBlock", "cinema", "code", cinema, code, ceoBlockByCinemaAndLanguageCode.isPresent());
        return ceoBlockByCinemaAndLanguageCode;
    }

    @Override
    public Optional<CeoBlock> getByHallAndLanguageCode(Hall hall, LanguageCode code) {
        LogUtil.logGetNotification("ceoBlock", "hall", "code", hall, code);
        Optional<CeoBlock> ceoBlockByHallAndLanguageCode = ceoBlockRepository.getByHallAndLanguageCode(hall, code);
        LogUtil.logGetInfo("ceoBlock", "hall", "code", hall, code, ceoBlockByHallAndLanguageCode.isPresent());
        return ceoBlockByHallAndLanguageCode;
    }

    @Override
    public Optional<CeoBlock> getByNewEntityAndLanguageCode(New newEntity, LanguageCode code) {
        LogUtil.logGetNotification("ceoBlock", "new", "code", newEntity, code);
        Optional<CeoBlock> ceoBlockByNewAndLanguageCode = ceoBlockRepository.getByNewEntityAndLanguageCode(newEntity, code);
        LogUtil.logGetInfo("ceoBlock", "new", "code", newEntity, code, ceoBlockByNewAndLanguageCode.isPresent());
        return ceoBlockByNewAndLanguageCode;
    }

    @Override
    public Optional<CeoBlock> getByShareAndLanguageCode(Share share, LanguageCode code) {
        LogUtil.logGetNotification("ceoBlock", "share", "code", share, code);
        Optional<CeoBlock> ceoBlockShareAndLanguageCode = ceoBlockRepository.getByShareAndLanguageCode(share, code);
        LogUtil.logGetInfo("ceoBlock", "share", "code", share, code, ceoBlockShareAndLanguageCode.isPresent());
        return ceoBlockShareAndLanguageCode;
    }

    @Override
    public Optional<CeoBlock> getByPageAndLanguageCode(Page page, LanguageCode code) {
        LogUtil.logGetNotification("ceoBlock", "page", "code", page, code);
        Optional<CeoBlock> ceoBlockPageAndLanguageCode = ceoBlockRepository.getByPageAndLanguageCode(page, code);
        LogUtil.logGetInfo("ceoBlock", "page", "code", page, code, ceoBlockPageAndLanguageCode.isPresent());
        return ceoBlockPageAndLanguageCode;
    }
}
