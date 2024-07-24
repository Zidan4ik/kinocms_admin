package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.repository.GalleryRepository;
import com.example.kinocms_admin.repository.HallRepository;
import com.example.kinocms_admin.service.HallService;
import com.example.kinocms_admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HallServiceImp implements HallService {
    private final HallRepository hallRepository;
    private final GalleryRepository galleryRepository;
    @Override
    public void save(Hall hall, MultipartFile schemaMF, MultipartFile bannerMF, List<MultipartFile> galleriesMF) {
        String uploadDir;
        String fileNameSchema = null;
        String fileNameBanner = null;
        HashMap<String, MultipartFile> mapFile = new HashMap<>();
        List<Gallery> galleriesRes = new ArrayList<>();
        if (hall.getId() != null) {
            Optional<Hall> hallById = hallRepository.findById(hall.getId());
            hall.setNameSchema(hallById.get().getNameSchema());
            hall.setNameBanner(hallById.get().getNameBanner());
        }
        if (schemaMF != null) {
            fileNameSchema = UUID.randomUUID() + "." + StringUtils.cleanPath(schemaMF.getOriginalFilename());
            hall.setNameSchema(fileNameSchema);
        }
        if (bannerMF != null) {
            fileNameBanner = UUID.randomUUID() + "." + StringUtils.cleanPath(bannerMF.getOriginalFilename());
            hall.setNameBanner(fileNameBanner);
        }

        if (hall.getId() != null) {
            List<Gallery> byFilm = galleryRepository.getAllByHall(hallRepository.findById(hall.getId()).get());
            hall.setGalleryList(byFilm);
        }
        if (galleriesMF != null) {
            for (MultipartFile fileGallery : galleriesMF) {
                if (fileGallery != null) {
                    String name = UUID.randomUUID() + "." + StringUtils.cleanPath(fileGallery.getOriginalFilename());
                    galleriesRes.add(new Gallery(name, GalleriesType.hall, hall));
                    mapFile.put(name, fileGallery);
                }
            }
        }
        hall.setGalleryList(galleriesRes);
        hallRepository.save(hall);

        try {
            if (schemaMF != null && !schemaMF.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/hall/schema/" + hall.getId();
                ImageUtil.saveAfterDelete(uploadDir, schemaMF, fileNameSchema);
            }
            if (bannerMF != null && !bannerMF.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/hall/banner/" + hall.getId();
                ImageUtil.saveAfterDelete(uploadDir, bannerMF, fileNameBanner);
            }
            if (!mapFile.isEmpty()) {
                uploadDir = "./uploads/hall/galleries/" + hall.getId();
                ImageUtil.savesAfterDelete(uploadDir, mapFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteById(Long id) {
        hallRepository.deleteById(id);
    }

    @Override
    public List<Hall> getAll() {
        return hallRepository.findAll();
    }

    @Override
    public Optional<Hall> getById(Long id) {
        return hallRepository.findById(id);
    }

    @Override
    public List<Hall> getAllByCinema(Cinema cinema) {
        return hallRepository.getAllByCinema(cinema);
    }
}
