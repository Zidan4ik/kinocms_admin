package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.model.GalleriesDTO;
import com.example.kinocms_admin.repository.GalleryRepository;
import com.example.kinocms_admin.service.GalleryService;
import com.example.kinocms_admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryServiceImp implements GalleryService {
    private final GalleryRepository galleryRepository;

    @Override
    public void save(Gallery galleries, MultipartFile file) {
        galleryRepository.save(galleries);
    }

    @Override
    public void delete(Long id) {
        galleryRepository.deleteById(id);
    }

    @Override
    public List<Gallery> getAllByFilm(Film film) {
        return galleryRepository.getAllByFilm(film);
    }

    @Override
    public List<Gallery> getAllByCinema(Cinema cinema) {
        return galleryRepository.getAllByCinema(cinema);
    }

    @Override
    public List<Gallery> getAllByHall(Hall hall) {
        return galleryRepository.getAllByHall(hall);
    }

    @Override
    public List<Gallery> getAllByPage(Page page) {
        return galleryRepository.getAllByPage(page);
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
                delete(g.getId());
            }
            if (g.getType() != null) {
                typePage = g.getType().toString();
            }
        }
        String upload = "./uploads/" + typePage + "/galleries/" + id;
        ImageUtil.deleteFiles(upload, noExistImages);
    }
}
