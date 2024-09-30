package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.mapper.GalleryMapper;
import com.example.kinocms_admin.model.GalleriesDTO;
import com.example.kinocms_admin.repository.GalleryRepository;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


import static java.util.Optional.empty;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GalleryServiceImpTest {
    @Mock
    private GalleryRepository galleryRepository;
    @InjectMocks
    private GalleryServiceImp galleryServiceImp;
    private List<Gallery> loadedGalleries;
    private final static Long ID = 1L;

    @BeforeEach
    void setUp() {
        loadedGalleries = TestDataUtil.loadGallery();
    }

    @Test
    void shouldSave() {
        galleryServiceImp.save(loadedGalleries.get(0));
        verify(galleryRepository, times(1)).save(loadedGalleries.get(0));
    }

    @Test
    void shouldDeleteGallery_ById() {
        galleryServiceImp.deleteById(ID);
        verify(galleryRepository, times(1)).deleteById(ID);
    }

    @Test
    void shouldDeleteGallery_ByFilm() {
        Film film = new Film();
        galleryServiceImp.deleteAllByFilm(film);
        verify(galleryRepository, times(1)).deleteAllByFilm(film);
    }

    @Test
    void shouldGetAllGallery_ByFilm() {
        Film film = new Film();
        when(galleryRepository.getAllByFilm(film)).thenReturn(loadedGalleries);
        List<Gallery> galleriesByFilm = galleryServiceImp.getAllByFilm(film);
        assertEquals(loadedGalleries.size(), galleriesByFilm.size(), "Sizes should be match");
        verify(galleryRepository, times(1)).getAllByFilm(film);
    }

    @Test
    void shouldGetAllGallery_ByCinema() {
        Cinema cinema = new Cinema();
        when(galleryRepository.getAllByCinema(cinema)).thenReturn(loadedGalleries);
        List<Gallery> galleriesByFilm = galleryServiceImp.getAllByCinema(cinema);
        assertEquals(loadedGalleries.size(), galleriesByFilm.size(), "Sizes should be match");
        verify(galleryRepository, times(1)).getAllByCinema(cinema);
    }

    @Test
    void shouldGetAllGallery_ByHall() {
        Hall hall = new Hall();
        when(galleryRepository.getAllByHall(hall)).thenReturn(loadedGalleries);
        List<Gallery> galleriesByFilm = galleryServiceImp.getAllByHall(hall);
        assertEquals(loadedGalleries.size(), galleriesByFilm.size(), "Sizes should be match");
        verify(galleryRepository, times(1)).getAllByHall(hall);
    }

    @Test
    void shouldGetAllGallery_ByPage() {
        Page page = new Page();
        when(galleryRepository.getAllByPage(page)).thenReturn(loadedGalleries);
        List<Gallery> galleriesByFilm = galleryServiceImp.getAllByPage(page);
        assertEquals(loadedGalleries.size(), galleriesByFilm.size(), "Sizes should be match");
        verify(galleryRepository, times(1)).getAllByPage(page);
    }

    @Test
    void shouldHandleDeletingImages_WhenSuccessfully() {
        Gallery galleryFromBD = new Gallery("image1.jpg", GalleriesType.pages, new Page());
        galleryFromBD.setId(ID);
        List<GalleriesDTO> galleriesDTOS = GalleryMapper.toDTOList(loadedGalleries);
        when(galleryRepository.findById(ID)).thenReturn(Optional.of(galleryFromBD));
        galleryServiceImp.handleDeletingImages(galleriesDTOS, ID);
    }

    @Test
    void shouldHandleDeletingImages_WhenTypeIsNull() {
        Gallery galleryFromBD = new Gallery("image1.jpg", null, new Page());
        galleryFromBD.setId(ID);
        List<GalleriesDTO> galleriesDTOS = GalleryMapper.toDTOList(loadedGalleries);
        when(galleryRepository.findById(ID)).thenReturn(Optional.of(galleryFromBD));
        galleryServiceImp.handleDeletingImages(galleriesDTOS, ID);
    }

    @Test
    void shouldHandleDeletingImages_WhenNestedIfOperatorsAreFalse() {
        Gallery galleryFromBD1 = new Gallery("", null, new Page());
        galleryFromBD1.setId(1L);
        List<GalleriesDTO> galleriesDTOS = GalleryMapper.toDTOList(loadedGalleries);
        when(galleryRepository.findById(1L)).thenReturn(empty());
        when(galleryRepository.findById(2L)).thenReturn(Optional.of(galleryFromBD1));
        galleryServiceImp.handleDeletingImages(galleriesDTOS, ID);
    }
}