package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.repository.CinemaRepository;
import com.example.kinocms_admin.repository.GalleryRepository;
import com.example.kinocms_admin.repository.MarkRepository;
import com.example.kinocms_admin.service.CinemaService;
import com.example.kinocms_admin.util.HandleDataUtil;
import com.example.kinocms_admin.util.ImageUtil;
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
    public void save(Cinema cinema, MultipartFile fileLogo, MultipartFile fileBanner, List<MultipartFile> galleriesMF) {
        String uploadDir;
        String fileNameSchema = null;
        String fileNameBanner = null;
        HashMap<String, MultipartFile> mapFile = new HashMap<>();
        List<Gallery> galleriesRes = new ArrayList<>();
        if (cinema.getId() != null) {
            Optional<Cinema> hallById = getById(cinema.getId());
            if (hallById.isPresent()) {
                cinema.setNameLogo(hallById.get().getNameLogo());
                cinema.setNameBanner(hallById.get().getNameBanner());
            }
            List<Gallery> galleryByCinema = galleryServiceImp.getAllByCinema(getById(cinema.getId()).get());
            if (!galleryByCinema.isEmpty()) {
                cinema.setGalleries(galleryByCinema);
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
        cinema.setMarksList(HandleDataUtil.findSimilarMark(cinema.getMarksList(),markServiceImp));
        cinemaRepository.save(cinema);

        try {
            if (fileLogo != null && !fileLogo.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/cinemas/logo/" + cinema.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileLogo, fileNameSchema);
            }
            if (fileBanner != null && !fileBanner.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/cinemas/banner/" + cinema.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileBanner, fileNameBanner);
            }
            if (!mapFile.isEmpty()) {
                uploadDir = "./uploads/cinemas/galleries/" + cinema.getId();
                ImageUtil.savesAfterDelete(uploadDir, mapFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        cinemaRepository.deleteById(id);
    }

    @Override
    public List<Cinema> getAll() {
        return cinemaRepository.findAll();
    }

    @Override
    public Optional<Cinema> getById(Long id) {
        return cinemaRepository.findById(id);
    }
}
