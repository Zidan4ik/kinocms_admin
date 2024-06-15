package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.repository.GalleryRepository;
import com.example.kinocms_admin.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryServiceImp implements GalleryService {
    private final GalleryRepository galleryRepository;

    @Override
    public void save(Gallery galleries, MultipartFile file) {
        galleryRepository.save(galleries);
    }

    @Override
    public void delete(long id) {
        galleryRepository.deleteById(id);
    }
    public List<Gallery> getAllByFilm(Film film){
        return galleryRepository.getAllByFilm(film);
    }
}
