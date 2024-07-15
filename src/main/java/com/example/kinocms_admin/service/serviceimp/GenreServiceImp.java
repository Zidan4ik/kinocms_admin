package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Genre;
import com.example.kinocms_admin.entity.Mark;
import com.example.kinocms_admin.repository.GenreRepository;
import com.example.kinocms_admin.service.GenreService;
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
    public void save(List<Genre> genres) {

    }

    public void saveIfNotExist(Set<Genre> genres) {
        for (Genre g : genres) {
            Optional<Genre> name = genreRepository.findByName(g.getName());
            if (!name.isPresent()) {
                genreRepository.save(g);
            }
        }
    }

    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }

    public void assignFilmToGenre(Set<Genre> genres, List<Film> films) {
        for (Genre genre : genres) {
            genre.setFilms(films);
        }
        genreRepository.saveAll(genres);
    }

    @Override
    public Set<Genre> getAllByFilms(List<Film> films) {
        return genreRepository.getAllByFilms(films);
    }

    public Set<Genre> getAllByFilm(Film film) {
        if (film != null) {
            List<Film> temporaryFilms = Collections.singletonList(film);
            Set<Genre> genresByFilm = genreRepository.getAllByFilms(temporaryFilms);
            return genresByFilm != null ? genresByFilm : Collections.emptySet();
        }
        return Collections.emptySet();
    }
}
