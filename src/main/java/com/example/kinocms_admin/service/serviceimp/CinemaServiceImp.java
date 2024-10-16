package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.repository.CinemaRepository;
import com.example.kinocms_admin.service.CinemaService;
import com.example.kinocms_admin.util.HandleDataUtil;
import com.example.kinocms_admin.util.ImageUtil;
import com.example.kinocms_admin.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CinemaServiceImp implements CinemaService {
    private final CinemaRepository cinemaRepository;
    private final GalleryServiceImp galleryServiceImp;
    private final MarkServiceImp markServiceImp;

    @Override
    public void save(Cinema cinema) {
        LogUtil.logSaveNotification("cinema", "id", cinema.getId());
        cinemaRepository.save(cinema);
        LogUtil.logSaveInfo("Cinema", "id", cinema.getId());
    }

    @Override
    public void saveCinema(Cinema cinema, MultipartFile fileLogo, MultipartFile fileBanner, List<MultipartFile> galleriesMF) {
        String uploadDir;
        String fileNameSchema = null;
        String fileNameBanner = null;
        HashMap<String, MultipartFile> mapFile = new HashMap<>();
        List<Gallery> galleriesRes = new ArrayList<>();
        if (cinema.getId() != null) {
            Optional<Cinema> cinemaById = getById(cinema.getId());
            if (cinemaById.isPresent()) {
                cinema.setNameLogo(cinemaById.get().getNameLogo());
                cinema.setNameBanner(cinemaById.get().getNameBanner());

                List<Gallery> galleryByCinema = galleryServiceImp.getAllByCinema(cinemaById.get());
                if (!galleryByCinema.isEmpty()) {
                    cinema.setGalleries(galleryByCinema);
                }
            }
            cinema.setCeoBlocks(null);
            cinema.setPageTranslations(null);
        }
        if (fileLogo != null) {
            fileNameSchema = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileLogo.getOriginalFilename()));
            cinema.setNameLogo(fileNameSchema);
        }
        if (fileBanner != null) {
            fileNameBanner = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileBanner.getOriginalFilename()));
            cinema.setNameBanner(fileNameBanner);
        }

        if (galleriesMF != null) {
            for (MultipartFile fileGallery : galleriesMF) {
                if (fileGallery != null) {
                    String name = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileGallery.getOriginalFilename()));
                    galleriesRes.add(new Gallery(name, GalleriesType.cinemas, cinema));
                    mapFile.put(name, fileGallery);
                }
            }
        }
        cinema.setGalleries(galleriesRes);
        cinema.setMarksList(HandleDataUtil.findSimilarMark(cinema.getMarksList(), markServiceImp));
        save(cinema);
        try {
            if (fileLogo != null && !fileLogo.getOriginalFilename().isEmpty()) {
                uploadDir = "/home/slj/projects/KinoCMS-R.Pravnyk/uploads/cinemas/logo/" + cinema.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileLogo, fileNameSchema);
            }
            if (fileBanner != null && !fileBanner.getOriginalFilename().isEmpty()) {
                uploadDir = "/home/slj/projects/KinoCMS-R.Pravnyk/uploads/cinemas/banner/" + cinema.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileBanner, fileNameBanner);
            }
            if (!mapFile.isEmpty()) {
                uploadDir = "/home/slj/projects/KinoCMS-R.Pravnyk/uploads/cinemas/galleries/" + cinema.getId();
                ImageUtil.savesAfterDelete(uploadDir, mapFile);
            }
        } catch (IOException e) {
            LogUtil.logErrorSavingFiles(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        LogUtil.logDeleteNotification("cinema", "id", id);
        clearMarks(id);
        cinemaRepository.deleteById(id);
        LogUtil.logDeleteInfo("Cinema", "id", id);
    }

    @Override
    public List<Cinema> getAll() {
        LogUtil.logGetAllNotification("cinemas");
        List<Cinema> cinemas = cinemaRepository.findAll();
        LogUtil.logSizeInfo("cinemas", cinemas.size());
        return cinemas;
    }

    @Override
    public Optional<Cinema> getById(Long id) {
        LogUtil.logGetNotification("cinema", "id", id);
        Optional<Cinema> cinemaById = cinemaRepository.findById(id);
        LogUtil.logGetInfo("Cinema", "id", id, cinemaById.isPresent());
        return cinemaById;
    }

    @Override
    public void clearMarks(Long id) {
        LogUtil.logClearMarksNotification("cinema");
        Optional<Cinema> cinemaById = getById(id);
        if(cinemaById.isPresent()){
            Cinema cinema = cinemaById.get();
            cinema.getMarksList().clear();
            save(cinema);
            LogUtil.logClearMarksInfo("cinema");
        }
    }
}
