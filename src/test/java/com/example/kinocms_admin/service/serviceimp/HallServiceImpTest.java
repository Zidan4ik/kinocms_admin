package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.repository.HallRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HallServiceImpTest {
    @Mock
    private HallRepository hallRepository;
    @Mock
    private GalleryServiceImp galleryServiceImp;
    @InjectMocks
    private HallServiceImp hallServiceImp;
    private List<Hall> loadedHalls;
    private MultipartFile fileSchema;
    private MultipartFile fileBanner;
    private final static Long ID = 1L;

    @BeforeEach
    void setUp() {
        loadedHalls = TestDataUtil.loadHalls();
        fileSchema = new MockMultipartFile("schema", "schema-image.file", "text/html", "content".getBytes());
        fileBanner = new MockMultipartFile("banner", "banner-image.file", "text/html", "content".getBytes());
    }

    @Test
    void shouldSave_Hall_WhenDataAreExist() {
        Hall hall = loadedHalls.get(0);
        when(hallRepository.findById(ID)).thenReturn(Optional.of(hall));
        when(galleryServiceImp.getAllByHall(hall)).thenReturn(new ArrayList<>());
        hallServiceImp.saveHall(hall, fileSchema, fileBanner, Arrays.asList(fileSchema, fileBanner, null));
        verify(hallRepository, times(1)).findById(ID);
        verify(galleryServiceImp, times(1)).getAllByHall(hall);
    }

    @Test
    void shouldSave_Hall_WhenDataAreNull() {
        Hall hall = loadedHalls.get(0);
        hall.setId(null);
        hallServiceImp.saveHall(hall, null, null, null);
    }

    @Test
    void shouldSave_Hall_WhenFilesOriginalNameAreEmpty() {
        Hall hall = loadedHalls.get(0);
        hall.setId(null);
        fileSchema = new MockMultipartFile("schema", "", "text/html", "content".getBytes());
        fileBanner = new MockMultipartFile("banner", "", "text/html", "content".getBytes());
        hallServiceImp.saveHall(hall, fileSchema, fileBanner, null);
    }

    @Test
    void shouldSave_Hall_WhenThrowException() {
        Hall hall = loadedHalls.get(0);
        try (MockedStatic<ImageUtil> mockedStatic = mockStatic(ImageUtil.class)) {
            mockedStatic.when(() -> ImageUtil.saveAfterDelete(anyString(), any(MultipartFile.class), anyString()))
                    .thenThrow(IOException.class);
            hallServiceImp.saveHall(hall, fileSchema, fileBanner, new ArrayList<>());
        }
    }

    @Test
    void deleteById() {
        hallServiceImp.deleteById(ID);
        verify(hallRepository,times(1)).deleteById(ID);
    }

    @Test
    void getAll() {
        when(hallRepository.findAll()).thenReturn(loadedHalls);
        List<Hall> halls = hallServiceImp.getAll();
        assertEquals(loadedHalls.size(),halls.size(),"Sizes should be match");
        verify(hallRepository,times(1)).findAll();
    }

    @Test
    void getById() {
        when(hallRepository.findById(ID)).thenReturn(Optional.of(loadedHalls.get(0)));
        Optional<Hall> hallById = hallServiceImp.getById(ID);
        assertTrue(hallById.isPresent(),"Hall was not found");
        assertEquals(1L,hallById.get().getId(),"Ids should be match");
        verify(hallRepository,times(1)).findById(ID);
    }

    @Test
    void getAllByCinema() {
        Cinema cinema = new Cinema();
        when(hallRepository.getAllByCinema(cinema)).thenReturn(loadedHalls);
        List<Hall> hallsByCinema = hallServiceImp.getAllByCinema(cinema);
        assertEquals(loadedHalls.size(),hallsByCinema.size(),"Sizes should be match");
        verify(hallRepository,times(1)).getAllByCinema(cinema);
    }

    @Test
    void deleteAllByCinema() {
        Cinema cinema = new Cinema();
        hallServiceImp.deleteAllByCinema(cinema);
        verify(hallRepository,times(1)).deleteAllByCinema(cinema);
    }
}