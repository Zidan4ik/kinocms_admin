package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.repository.HallRepository;
import com.example.kinocms_admin.service.HallService;
import com.example.kinocms_admin.util.ImageUtil;
import com.example.kinocms_admin.util.LogUtil;
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
    public void save(Hall hall) {
        LogUtil.logSaveNotification("hall", "id", hall.getId());
        hallRepository.save(hall);
        LogUtil.logSaveInfo("hall", "id", hall.getId());
    }

    @Override
    public void saveHall(Hall hall, MultipartFile schemaMF, MultipartFile bannerMF, List<MultipartFile> galleriesMF) {
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
            for (MultipartFile file : galleriesMF) {
                if (file != null) {
                    String name = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                    galleriesRes.add(new Gallery(name, GalleriesType.halls, hall));
                    mapFile.put(name, file);
                }
            }
        }
        hall.setGalleryList(galleriesRes);
        save(hall);

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
            LogUtil.logErrorSavingFiles(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        LogUtil.logDeleteNotification("hall", "id", id);
        hallRepository.deleteById(id);
        LogUtil.logDeleteInfo("Hall", "id", id);
    }

    @Override
    public List<Hall> getAll() {
        LogUtil.logGetAllNotification("halls");
        List<Hall> halls = hallRepository.findAll();
        LogUtil.logSizeInfo("halls", halls.size());
        return halls;
    }

    @Override
    public Optional<Hall> getById(Long id) {
        LogUtil.logGetNotification("hall", "id", id);
        Optional<Hall> hallById = hallRepository.findById(id);
        LogUtil.logGetInfo("Hall", "id", id, hallById.isPresent());
        return hallById;
    }

    @Override
    public List<Hall> getAllByCinema(Cinema cinema) {
        LogUtil.logGetAllNotification("halls", "cinema", cinema);
        List<Hall> hallsByCinema = hallRepository.getAllByCinema(cinema);
        LogUtil.logSizeInfo("halls", hallsByCinema.size());
        return hallsByCinema;
    }

    @Transactional
    @Override
    public void deleteAllByCinema(Cinema cinema) {
        LogUtil.logDeleteNotification("halls", "cinema", cinema);
        hallRepository.deleteAllByCinema(cinema);
        LogUtil.logDeleteAllInfo("Halls","cinema");
    }
}
