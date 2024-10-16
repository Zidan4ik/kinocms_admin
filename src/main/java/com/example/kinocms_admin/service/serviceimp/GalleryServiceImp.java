package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.model.GalleriesDTO;
import com.example.kinocms_admin.repository.GalleryRepository;
import com.example.kinocms_admin.service.GalleryService;
import com.example.kinocms_admin.util.ImageUtil;
import com.example.kinocms_admin.util.LogUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryServiceImp implements GalleryService {
    private final GalleryRepository galleryRepository;

    @Override
    public void save(Gallery gallery) {
        LogUtil.logSaveNotification("gallery", "id", gallery.getId());
        galleryRepository.save(gallery);
        LogUtil.logSaveAllInfo("gallery");
    }

    @Override
    public void deleteById(Long id) {
        LogUtil.logDeleteNotification("gallery", "id", id);
        galleryRepository.deleteById(id);
        LogUtil.logDeleteInfo("Gallery", "id", id);
    }

    @Override
    @Transactional
    public void deleteAllByFilm(Film film) {
        LogUtil.logDeleteAllNotification("gallery", "film", film);
        galleryRepository.deleteAllByFilm(film);
        LogUtil.logDeleteAllInfo("Gallery","film");
    }

    @Override
    public List<Gallery> getAllByFilm(Film film) {
        LogUtil.logGetAllNotification("gallery", "film", film);
        List<Gallery> galleryByFilm = galleryRepository.getAllByFilm(film);
        LogUtil.logSizeInfo("gallery by film", galleryByFilm.size());
        return galleryByFilm;
    }

    @Override
    public List<Gallery> getAllByCinema(Cinema cinema) {
        LogUtil.logGetAllNotification("gallery", "cinema", cinema);
        List<Gallery> galleryByCinema = galleryRepository.getAllByCinema(cinema);
        LogUtil.logSizeInfo("gallery by cinema", galleryByCinema.size());
        return galleryByCinema;
    }

    @Override
    public List<Gallery> getAllByHall(Hall hall) {
        LogUtil.logGetAllNotification("gallery", "hall", hall);
        List<Gallery> galleryByHall = galleryRepository.getAllByHall(hall);
        LogUtil.logSizeInfo("gallery by hall", galleryByHall.size());
        return galleryByHall;
    }

    @Override
    public List<Gallery> getAllByPage(Page page) {
        LogUtil.logGetAllNotification("gallery", "page", page);
        List<Gallery> galleryByPage = galleryRepository.getAllByPage(page);
        LogUtil.logSizeInfo("gallery by page", galleryByPage.size());
        return galleryByPage;
    }

    public void handleDeletingImages(List<GalleriesDTO> galleries, long id) {
        List<String> noExistImages = new ArrayList<>();
        String typePage = null;
        for (GalleriesDTO g : galleries) {
            if (g.getName() == null) {
                Optional<Gallery> image = galleryRepository.findById(g.getId());
                if (image.isPresent() && !image.get().getLinkImage().isEmpty()) {
                    noExistImages.add(image.get().getLinkImage());
                }
                deleteById(g.getId());
            }
            if (g.getType() != null) {
                typePage = g.getType().toString();
            }
        }
        String upload = "/home/slj/projects/KinoCMS-R.Pravnyk/uploads/" + typePage + "/galleries/" + id;
        ImageUtil.deleteFiles(upload, noExistImages);
    }
}
