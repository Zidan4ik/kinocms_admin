package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.repository.FilmRepository;
import com.example.kinocms_admin.service.FilmService;
import com.example.kinocms_admin.util.HandleDataUtil;
import com.example.kinocms_admin.util.ImageUtil;
import com.example.kinocms_admin.util.LogUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImp implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreServiceImp genreServiceImp;
    private final MarkServiceImp markServiceImp;

    @Override
    public void save(Film film) {
        LogUtil.logSaveNotification("film", "id", film.getId());
        filmRepository.save(film);
        LogUtil.logSaveInfo("film", "id", film.getId());
    }

    @Override
    public void saveFilm(Film film, MultipartFile file, List<MultipartFile> multipartFiles) {
        String uploadDir;
        String fileName = null;
        HashMap<String, MultipartFile> mapFile = new HashMap<>();
        List<Gallery> galleriesRes = new ArrayList<>();
        if (film.getId() != null) {
            Optional<Film> filmById = getById(film.getId());
            filmById.ifPresent(f -> {
                film.setNameImage(f.getNameImage());
                film.setGalleries(f.getGalleries());
            });
        }
        if (file != null) {
            fileName = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            film.setNameImage(fileName);
        }
        if (multipartFiles != null) {
            for (MultipartFile fileGalleries : multipartFiles) {
                if (fileGalleries != null) {
                    String name = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileGalleries.getOriginalFilename()));
                    galleriesRes.add(new Gallery(name, GalleriesType.films, film));
                    mapFile.put(name, fileGalleries);
                }
            }
        }
        film.setGalleries(galleriesRes);
        film.setGenresList(HandleDataUtil.findSimilarGenre(film.getGenresList(), genreServiceImp));
        film.setMarksList(HandleDataUtil.findSimilarMark(film.getMarksList(), markServiceImp));
        save(film);

        try {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/films/main-image/" + film.getId();
                ImageUtil.saveAfterDelete(uploadDir, file, fileName);
            }
            if (!mapFile.isEmpty()) {
                uploadDir = "./uploads/films/galleries/" + film.getId();
                ImageUtil.savesAfterDelete(uploadDir, mapFile);
            }
        } catch (IOException e) {
            LogUtil.logErrorSavingFiles(e);
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        LogUtil.logDeleteNotification("film", "id", id);
        filmRepository.deleteById(id);
        LogUtil.logDeleteInfo("film", "id", id);
    }

    @Override
    public List<Film> getAll() {
        LogUtil.logGetAllNotification("films");
        List<Film> films = filmRepository.findAll();
        LogUtil.logSizeInfo("films", films.size());
        return films;
    }

    @Override
    public Optional<Film> getById(long id) {
        LogUtil.logGetNotification("film", "id", id);
        Optional<Film> filmById = filmRepository.findById(id);
        LogUtil.logGetInfo("Film", "id", id, filmById.isPresent());
        return filmById;
    }

    @Override
    public List<Film> findFilmsIsActive(boolean status) {
        LogUtil.logGetNotification("films", "status", status);
        List<Film> films = new ArrayList<>();
        LocalDate today = LocalDate.now();
        if (status) {
            for (Film film : getAll()) {
                if ((film.getDateStart().isBefore(today) || film.getDateStart().equals(today)) && (film.getDateEnd().isAfter(today) || film.getDateEnd().equals(today))) {
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
        LogUtil.logSizeInfo("films", films.size());
        return films;
    }
}
