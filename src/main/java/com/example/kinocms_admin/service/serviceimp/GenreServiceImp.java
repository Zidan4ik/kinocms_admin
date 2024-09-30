package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Genre;
import com.example.kinocms_admin.repository.GenreRepository;
import com.example.kinocms_admin.service.GenreService;
import com.example.kinocms_admin.util.LogUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreServiceImp implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public void deleteById(long id) {
        LogUtil.logDeleteNotification("genre", "id", id);
        genreRepository.deleteById(id);
        LogUtil.logDeleteInfo("Genre", "id", id);
    }

    @Override
    @Transactional
    public void deleteAllByFilms(List<Film> films) {
        LogUtil.logDeleteAllNotification("genres", "films", films.size());
        genreRepository.deleteAllByFilms(films);
        LogUtil.logDeleteAllInfo("genres", "films");
    }

    @Override
    public Set<Genre> getAllByFilms(List<Film> films) {
        LogUtil.logGetAllNotification("genres", "films", films.size());
        Set<Genre> genresByFilms = genreRepository.getAllByFilms(films);
        LogUtil.logSizeInfo("genres by films", genresByFilms.size());
        return genresByFilms;
    }

    @Override
    public Set<Genre> getAllByFilm(Film film) {
        LogUtil.logGetAllNotification("genres", "film", film);
        if (film != null) {
            List<Film> temporaryFilms = Collections.singletonList(film);
            Set<Genre> genresByFilm = genreRepository.getAllByFilms(temporaryFilms);
            LogUtil.logSizeInfo("marks", genresByFilm.size());
            return genresByFilm;
        }
        LogUtil.logItemNull("film");
        return Collections.emptySet();
    }

    @Override
    public Optional<Genre> getByName(String name) {
        LogUtil.logGetNotification("genre", "name", name);
        Optional<Genre> genreByName = genreRepository.findByName(name);
        LogUtil.logGetInfo("Genre", "name", name,genreByName.isPresent());
        return genreByName;
    }
}
