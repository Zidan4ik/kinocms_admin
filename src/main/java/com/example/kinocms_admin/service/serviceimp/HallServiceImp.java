package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.repository.HallRepository;
import com.example.kinocms_admin.service.HallService;
import com.example.kinocms_admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HallServiceImp implements HallService {
    private final HallRepository hallRepository;
    private final GalleryServiceImp galleryServiceImp;

    @Override
    public void save(Hall hall, MultipartFile schemaMF, MultipartFile bannerMF, List<MultipartFile> galleriesMF) {
        String uploadDir;
        String fileNameSchema = null;
        String fileNameBanner = null;
        HashMap<String, MultipartFile> mapFile = new HashMap<>();
        List<Gallery> galleriesRes = new ArrayList<>();
        if (hall.getId() != null) {
            Optional<Hall> hallById = hallRepository.findById(hall.getId());
            hallById.ifPresent(h -> hall.setNameSchema(h.getNameSchema()));
            hallById.ifPresent(h -> hall.setNameBanner(h.getNameBanner()));
            hallById.ifPresent(h ->
                    hall.setGalleryList(galleryServiceImp.getAllByHall(h)));

            hall.setPageTranslations(null);
            hall.setCeoBlocks(null);
        }
        if (schemaMF != null) {
            fileNameSchema = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(schemaMF.getOriginalFilename()));
            hall.setNameSchema(fileNameSchema);
        }
        if (bannerMF != null) {
            fileNameBanner = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(bannerMF.getOriginalFilename()));
            hall.setNameBanner(fileNameBanner);
        }

        if (galleriesMF != null) {
            for (MultipartFile fileGallery : galleriesMF) {
                if (fileGallery != null) {
                    String name = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileGallery.getOriginalFilename()));
                    galleriesRes.add(new Gallery(name, GalleriesType.halls, hall));
                    mapFile.put(name, fileGallery);
                }
            }
        }
        hall.setGalleryList(galleriesRes);
        hallRepository.save(hall);

        try {
            if (schemaMF != null && !schemaMF.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/halls/schema/" + hall.getId();
                ImageUtil.saveAfterDelete(uploadDir, schemaMF, fileNameSchema);
            }
            if (bannerMF != null && !bannerMF.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/halls/banner/" + hall.getId();
                ImageUtil.saveAfterDelete(uploadDir, bannerMF, fileNameBanner);
            }
            if (!mapFile.isEmpty()) {
                uploadDir = "./uploads/halls/galleries/" + hall.getId();
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
    @Transactional
    @Override
    public void deleteAllByCinema(Cinema cinema) {
        hallRepository.deleteAllByCinema(cinema);
    }
    public Integer getAmountHalls(){
        return hallRepository.findAll().size();
    }
}
