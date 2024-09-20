package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.repository.MarkRepository;
import com.example.kinocms_admin.service.MarkService;
import com.example.kinocms_admin.util.LogUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarkServiceImp implements MarkService {
    private final MarkRepository markRepository;

    @Override
    public void saveAll(Set<Mark> marks) {
        LogUtil.logSaveAllNotifications("marks");
        markRepository.saveAll(marks);
        LogUtil.logSaveAllInfo("marks");
    }

    @Override
    public void deleteById(long id) {
        LogUtil.logDeleteNotification("mark", "id", id);
        markRepository.deleteById(id);
        LogUtil.logDeleteInfo("mark", "id", id);
    }

    @Override
    @Transactional
    public void deleteAllByFilms(List<Film> films) {
        LogUtil.logDeleteAllNotification("marks", "films", films.size());
        markRepository.deleteAllByFilms(films);
        LogUtil.logDeleteAllInfo("marks", "films");
    }

    @Override
    public Set<Mark> getAllByFilms(List<Film> films) {
        LogUtil.logGetAllNotification("marks", "films", films.size());
        Set<Mark> marks = markRepository.getAllByFilms(films);
        LogUtil.logSizeInfo("marks by films", marks.size());
        return marks;
    }

    @Override
    public Set<Mark> getAllByNews(List<New> newEntity) {
        LogUtil.logGetAllNotification("marks", "news", newEntity.size());
        Set<Mark> marksByNews = markRepository.getAllByNews(newEntity);
        LogUtil.logSizeInfo("marks by news", marksByNews.size());
        return marksByNews;
    }

    @Override
    public Optional<Mark> getByName(String name) {
        LogUtil.logGetNotification("mark", "name", name);
        Optional<Mark> markByName = markRepository.findByName(name);
        LogUtil.logGetInfo("mark", "name", name, markByName.isPresent());
        return markByName;
    }

    @Override
    public Set<Mark> getAllByShares(List<Share> shares) {
        LogUtil.logGetAllNotification("marks", "news", shares.size());
        Set<Mark> marksByShares = markRepository.getAllByShares(shares);
        LogUtil.logSizeInfo("marks by shares", marksByShares.size());
        return marksByShares;
    }

    @Override
    public Set<Mark> getAllByFilm(Film film) {
        LogUtil.logGetAllNotification("marks", "film", film);
        if (film != null) {
            List<Film> temporaryFilms = Collections.singletonList(film);
            Set<Mark> marksByFilm = getAllByFilms(temporaryFilms);
            LogUtil.logSizeInfo("marks by film", marksByFilm.size());
            return marksByFilm;
        }
        LogUtil.logItemNull("film");
        return Collections.emptySet();
    }

    @Override
    public Set<Mark> getAllByNew(New newEntity) {
        LogUtil.logGetAllNotification("marks", "new", newEntity);
        if (newEntity != null) {
            List<New> singletonList = Collections.singletonList(newEntity);
            Set<Mark> marksByNew = getAllByNews(singletonList);
            LogUtil.logSizeInfo("marks by new", marksByNew.size());
            return marksByNew;
        }
        LogUtil.logItemNull("new");
        return Collections.emptySet();
    }

    @Override
    public Set<Mark> getAllByShare(Share share) {
        LogUtil.logGetAllNotification("marks", "share", share);
        if (share != null) {
            List<Share> singletonList = Collections.singletonList(share);
            Set<Mark> marksByShare = getAllByShares(singletonList);
            LogUtil.logSizeInfo("marks by share", marksByShare.size());
            return marksByShare;
        }
        LogUtil.logItemNull("share");
        return Collections.emptySet();
    }

    @Override
    public Set<Mark> getAllByCinema(Cinema cinema) {
        LogUtil.logGetAllNotification("marks", "cinema", cinema);
        if (cinema != null) {
            List<Cinema> temporaryFilms = Collections.singletonList(cinema);
            Set<Mark> marksByCinema = markRepository.getAllByCinemas(temporaryFilms);
            LogUtil.logSizeInfo("marks by cinema", marksByCinema.size());
            return marksByCinema;
        }
        LogUtil.logItemNull("cinema");
        return Collections.emptySet();
    }
}
