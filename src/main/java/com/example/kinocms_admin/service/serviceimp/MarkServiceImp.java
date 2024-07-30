package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.repository.MarkRepository;
import com.example.kinocms_admin.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MarkServiceImp implements MarkService {
    private final MarkRepository markRepository;

    @Override
    public void save(Set<Mark> marks) {
        markRepository.saveAll(marks);
    }

    @Override
    public void deleteById(long id) {
        markRepository.deleteById(id);
    }

    @Override
    public Set<Mark> getAllByFilms(List<Film> films) {
        return markRepository.getAllByFilms(films);
    }

    @Override
    public Set<Mark> getAllByNews(List<New> newEntity) {
        return markRepository.getAllByNews(newEntity);
    }

    @Override
    public Optional<Mark> getByName(String name) {
        return markRepository.findByName(name);
    }

    @Override
    public Set<Mark> getAllByShares(List<Share> shares) {
        return markRepository.getAllByShares(shares);
    }

    @Override
    public Set<Mark> getAllByFilm(Film film) {
        if (film != null) {
            List<Film> temporaryFilms = Collections.singletonList(film);
            Set<Mark> marksByFilm = getAllByFilms(temporaryFilms);
            return marksByFilm != null ? marksByFilm : Collections.emptySet();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<Mark> getAllByNew(New newEntity) {
        if (newEntity != null) {
            List<New> singletonList = Collections.singletonList(newEntity);
            Set<Mark> marksByNew = getAllByNews(singletonList);
            return marksByNew != null ? marksByNew : Collections.emptySet();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<Mark> getAllByNew(Share share) {
        if (share != null) {
            List<Share> singletonList = Collections.singletonList(share);
            Set<Mark> marksByShare = getAllByShares(singletonList);
            return marksByShare != null ? marksByShare : Collections.emptySet();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<Mark> getAllByCinema(Cinema cinema) {
        if (cinema != null) {
            List<Cinema> temporaryFilms = Collections.singletonList(cinema);
            Set<Mark> marksByCinema = markRepository.getAllByCinemas(temporaryFilms);
            return marksByCinema != null ? marksByCinema : Collections.emptySet();
        }
        return Collections.emptySet();
    }
}
