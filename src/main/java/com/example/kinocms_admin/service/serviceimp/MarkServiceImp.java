package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Mark;
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

    public Set<Mark> getAllByFilms(List<Film> films) {
        return markRepository.getAllByFilms(films);
    }

    public Set<Mark> getAllByFilm(Film film) {
        if (film != null) {
            List<Film> temporaryFilms = Collections.singletonList(film);
            Set<Mark> marksByFilm = markRepository.getAllByFilms(temporaryFilms);
            return marksByFilm != null ? marksByFilm : Collections.emptySet();
        }
        return Collections.emptySet();
    }
    public Set<Mark> getAllByCinema(Cinema cinema) {
        if (cinema != null) {
            List<Cinema> temporaryFilms = Collections.singletonList(cinema);
            Set<Mark> marksByFilm = markRepository.getAllByCinemas(temporaryFilms);
            return marksByFilm != null ? marksByFilm : Collections.emptySet();
        }
        return Collections.emptySet();
    }
}
