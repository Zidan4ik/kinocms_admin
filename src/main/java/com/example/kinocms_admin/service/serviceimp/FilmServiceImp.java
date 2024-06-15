package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.repository.FilmRepository;
import com.example.kinocms_admin.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmServiceImp implements FilmService {
    private final FilmRepository filmRepository;
    @Override
    public void save(Film film, MultipartFile multipartFile) {
        filmRepository.save(film);
    }

    @Override
    public void delete(long id) {
        filmRepository.deleteById(id);
    }

    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    public Optional<Film> getById(long id) {
        return filmRepository.findById(id);
    }
    public List<Film> findFilmsIsActive(boolean status){
        List<Film> films = new ArrayList<>();
        LocalDate today = LocalDate.now();
        if(status){
            for (Film film:getAll()){
                if((film.getDateStart().isBefore(today) || film.getDateStart().equals(today)) &&
                        (film.getDateEnd().isAfter(today) || film.getDateEnd().equals(today))){
                    films.add(film);
                }
            }
        }else{
            for (Film film:getAll()){
                if (film.getDateStart().isAfter(today) || film.getDateEnd().isBefore(today)) {
                    films.add(film);
                }
            }
        }
        return films;
    }
}
