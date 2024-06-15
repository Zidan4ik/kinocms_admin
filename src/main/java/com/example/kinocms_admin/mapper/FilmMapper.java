package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.model.FilmsDTOView;
import com.example.kinocms_admin.util.MonthTranslator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmMapper {
    public static List<FilmsDTOView> toDTOViewFilms(List<Film> filmsEntity){
        List<FilmsDTOView> dtoList = new ArrayList<>();
        for(Film film:filmsEntity){
            FilmsDTOView dto = new FilmsDTOView();
            dto.setId(film.getId());
            dto.setName(film.getName());
            dto.setImage(film.getMainImage());
            String date = film.getDateStart().getDayOfMonth()+" "+ MonthTranslator.translate(film.getDateStart().getMonth()) + " - "
                    + film.getDateEnd().getDayOfMonth()+" "+MonthTranslator.translate(film.getDateEnd().getMonth());
            dto.setDate(date);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
