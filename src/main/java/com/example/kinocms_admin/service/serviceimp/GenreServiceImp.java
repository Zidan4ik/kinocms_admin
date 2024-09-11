package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Genre;
import com.example.kinocms_admin.entity.Mark;
import com.example.kinocms_admin.repository.GenreRepository;
import com.example.kinocms_admin.service.GenreService;
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
        genreRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByFilms(List<Film> films) {
        genreRepository.deleteAllByFilms(films);
    }

    @Override
    public Set<Genre> getAllByFilms(List<Film> films) {
        return genreRepository.getAllByFilms(films);
    }

    @Override
    public Set<Genre> getAllByFilm(Film film) {
        if (film != null) {
            List<Film> temporaryFilms = Collections.singletonList(film);
            Set<Genre> genresByFilm = genreRepository.getAllByFilms(temporaryFilms);
            return genresByFilm != null ? genresByFilm : Collections.emptySet();
        }
        return Collections.emptySet();
    }

    @Override
    public Optional<Genre> getByName(String name) {
        return genreRepository.findByName(name);
    }
}
