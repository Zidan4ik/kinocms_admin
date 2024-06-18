package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Genre;
import com.example.kinocms_admin.repository.GenreRepository;
import com.example.kinocms_admin.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImp implements GenreService {
    private final GenreRepository genreRepository;
    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public List<Genre> getAllByFilm(Film film) {
      return genreRepository.getAllByFilm(film);
    }
}
