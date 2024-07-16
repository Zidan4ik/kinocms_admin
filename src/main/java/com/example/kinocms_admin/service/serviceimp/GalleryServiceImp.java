package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Gallery;
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
    public void handleDeletingImages(List<GalleriesDTO> galleries, long id) {
        List<String> noExistImages = new ArrayList<>();
        for (GalleriesDTO g : galleries) {
            if(g.getName()==null){
                Optional<Gallery> image = galleryRepository.findById(g.getId());
                if(image.isPresent() && !image.get().getLinkImage().isEmpty()){
                    noExistImages.add(image.get().getLinkImage());
                }
                delete(g.getId());
            }
        }
        String upload = "./uploads/film/galleries/"+id;
        ImageUtil.deleteFiles(upload,noExistImages);
    }

    public List<Gallery> getAllByFilm(Film film) {
        return galleryRepository.getAllByFilm(film);
    }
    public List<Gallery> getAllByCinema(Cinema cinema) {
        return galleryRepository.getAllByCinema(cinema);
    }
}
