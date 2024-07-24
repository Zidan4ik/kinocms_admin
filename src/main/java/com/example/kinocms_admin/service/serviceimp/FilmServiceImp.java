package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.repository.FilmRepository;
import com.example.kinocms_admin.repository.GalleryRepository;
import com.example.kinocms_admin.repository.GenreRepository;
import com.example.kinocms_admin.repository.MarkRepository;
import com.example.kinocms_admin.service.FilmService;
import com.example.kinocms_admin.util.HandleDataUtil;
import com.example.kinocms_admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImp implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final MarkRepository markRepository;
    private final GalleryRepository galleryRepository;

    @Override
    public void save(Film film, MultipartFile file, List<MultipartFile> multipartFiles) {
        String uploadDir;
        String fileName = null;
        HashMap<String, MultipartFile> mapFile = new HashMap<>();
        List<Gallery> galleriesRes = new ArrayList<>();
        if (film.getId() != null) {
            Optional<Film> filmById = filmRepository.findById(film.getId());
            film.setNameImage(filmById.get().getNameImage());
        }
        if (file != null) {
            fileName = UUID.randomUUID() + "." + StringUtils.cleanPath(file.getOriginalFilename());
            film.setNameImage(fileName);
        }

        if (film.getId() != null) {
            List<Gallery> byFilm = galleryRepository.getAllByFilm(filmRepository.findById(film.getId()).get());
            film.setGalleries(byFilm);
        }
        if (multipartFiles != null) {
            for (MultipartFile fileGalleries : multipartFiles) {
                if (fileGalleries != null) {
                    String name = UUID.randomUUID() + "." + StringUtils.cleanPath(fileGalleries.getOriginalFilename());
                    galleriesRes.add(new Gallery(name, GalleriesType.films, film));
                    mapFile.put(name, fileGalleries);
                }
            }
        }

        film.setGalleries(galleriesRes);
        film.setGenresList(HandleDataUtil.findSimilarGenre(film.getGenresList(),genreRepository));
        film.setMarksList(HandleDataUtil.findSimilarMark(film.getMarksList(),markRepository));
        filmRepository.save(film);

        try {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/film/main-image/" + film.getId();
                ImageUtil.saveAfterDelete(uploadDir, file, fileName);
            }
            if (!mapFile.isEmpty()) {
                uploadDir = "./uploads/film/galleries/" + film.getId();
                ImageUtil.savesAfterDelete(uploadDir, mapFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(long id) {
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

    @Override
    public List<Film> findFilmsIsActive(boolean status) {
        List<Film> films = new ArrayList<>();
        LocalDate today = LocalDate.now();
        if (status) {
            for (Film film : getAll()) {
                if ((film.getDateStart().isBefore(today) || film.getDateStart().equals(today)) &&
                        (film.getDateEnd().isAfter(today) || film.getDateEnd().equals(today))) {
                    films.add(film);
                }
            }
        } else {
            for (Film film : getAll()) {
                if (film.getDateStart().isAfter(today) && film.getDateEnd().isAfter(today)) {
                    films.add(film);
                }
            }
        }
        return films;
    }
}
