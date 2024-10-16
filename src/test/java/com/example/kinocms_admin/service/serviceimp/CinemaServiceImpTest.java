package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.repository.CinemaRepository;
import com.example.kinocms_admin.util.ImageUtil;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaServiceImpTest {
    @Mock
    private CinemaRepository cinemaRepository;
    @Mock
    private GalleryServiceImp galleryServiceImp;
    @Mock
    private MarkServiceImp markServiceImp;
    @InjectMocks
    private CinemaServiceImp cinemaServiceImp;
    private List<Cinema> loadedCinemas;
    private final static Long ID = 1L;
    private MultipartFile fileLogo;
    private MultipartFile fileBanner;

    @BeforeEach
    void setUp() {
        loadedCinemas = TestDataUtil.loadCinemas();
    }

    @Test
    void shouldSave() {
        cinemaServiceImp.save(loadedCinemas.get(0));
        verify(cinemaRepository, times(1)).save(loadedCinemas.get(0));
    }

    @Test
    void shouldSaveCinema_WithSuccessfullyProcess() {
        fileLogo = new MockMultipartFile("logo-image", "cinema1.html", "text/html", "content".getBytes());
        fileBanner = new MockMultipartFile("banner-image", "cinema1.html", "text/html", "content".getBytes());

        when(cinemaRepository.findById(ID)).thenReturn(Optional.of(loadedCinemas.get(0)));
        when(galleryServiceImp.getAllByCinema(loadedCinemas.get(0))).thenReturn(TestDataUtil.loadGallery());

        cinemaServiceImp.saveCinema(loadedCinemas.get(0), fileLogo, fileBanner, Arrays.asList(fileLogo, null));

        verify(cinemaRepository, times(1)).findById(ID);
        verify(galleryServiceImp, times(1)).getAllByCinema(loadedCinemas.get(0));
    }
    @Test
    void shouldSaveCinema_WhenFilesAreNull() {
        when(cinemaRepository.findById(ID)).thenReturn(Optional.of(loadedCinemas.get(0)));
        when(galleryServiceImp.getAllByCinema(loadedCinemas.get(0))).thenReturn(TestDataUtil.loadGallery());

        cinemaServiceImp.saveCinema(loadedCinemas.get(0), null,null, null);

        verify(cinemaRepository, times(1)).findById(ID);
        verify(galleryServiceImp, times(1)).getAllByCinema(loadedCinemas.get(0));
    }
    @Test
    void shouldSaveCinema_WhenFilesOriginalNamesAreEmpty(){
        fileLogo = new MockMultipartFile("logo-image", "", "text/html", "content".getBytes());
        fileBanner = new MockMultipartFile("banner-image", "", "text/html", "content".getBytes());

        when(cinemaRepository.findById(ID)).thenReturn(Optional.of(loadedCinemas.get(0)));
        when(galleryServiceImp.getAllByCinema(loadedCinemas.get(0))).thenReturn(TestDataUtil.loadGallery());

        cinemaServiceImp.saveCinema(loadedCinemas.get(0), fileLogo,fileBanner, null);

        verify(cinemaRepository, times(1)).findById(ID);
        verify(galleryServiceImp, times(1)).getAllByCinema(loadedCinemas.get(0));
    }

    @Test
    void shouldSaveCinema_WhenThrowException() {
        Cinema cinema = loadedCinemas.get(2);
        fileLogo = new MockMultipartFile("logo-image", "cinema1.html", "text/html", "content".getBytes());
        fileBanner = new MockMultipartFile("banner-image", "cinema1.html", "text/html", "content".getBytes());

        try (MockedStatic<ImageUtil> mockedStatic = mockStatic(ImageUtil.class)) {
            mockedStatic.when(() -> ImageUtil.saveAfterDelete(anyString(), any(MultipartFile.class), anyString()))
                    .thenThrow(IOException.class);
            cinemaServiceImp.saveCinema(cinema, fileLogo, fileBanner, Arrays.asList(fileLogo, null));
        }
        verify(cinemaRepository).save(any(Cinema.class));
    }
    @Test
    void shouldSaveCinema_WhenCinemasIsEmpty(){
        when(cinemaRepository.findById(ID)).thenReturn(Optional.of(loadedCinemas.get(0)));
        when(galleryServiceImp.getAllByCinema(loadedCinemas.get(0))).thenReturn(Collections.emptyList());
        cinemaServiceImp.saveCinema(loadedCinemas.get(0), fileLogo,fileBanner, null);
        verify(cinemaRepository, times(1)).findById(ID);
        verify(galleryServiceImp, times(1)).getAllByCinema(loadedCinemas.get(0));
    }
    @Test
    void shouldSaveCinema_WhenCinemaIdIsNull(){
        loadedCinemas.get(0).setId(null);
        cinemaServiceImp.saveCinema(loadedCinemas.get(0), fileLogo,fileBanner, null);
        verify(cinemaRepository,times(1)).save(loadedCinemas.get(0));
    }
    @Test
    void shouldSaveCinema_WhenCinemaIsEmpty(){
        when(cinemaRepository.findById(anyLong())).thenReturn(empty());
        cinemaServiceImp.saveCinema(loadedCinemas.get(0), fileLogo,fileBanner, null);
        verify(cinemaRepository, times(1)).findById(ID);
        verify(cinemaRepository,times(1)).save(loadedCinemas.get(0));
    }

    @Test
    void shouldDeleteCinema_ById() {
        cinemaServiceImp.deleteById(ID);
        verify(cinemaRepository, times(1)).deleteById(ID);
    }

    @Test
    void shouldGetAllCinemas() {
        when(cinemaRepository.findAll()).thenReturn(loadedCinemas);
        List<Cinema> cinemas = cinemaServiceImp.getAll();
        assertEquals(loadedCinemas.size(),cinemas.size(),"Sizes should be match");
        verify(cinemaRepository,times(1)).findAll();
    }
    @Test
    void shouldClearMarks(){
        when(cinemaRepository.findById(ID)).thenReturn(Optional.of(loadedCinemas.get(0)));
        cinemaServiceImp.clearMarks(ID);
        verify(cinemaRepository,times(1)).findById(ID);
    }
}