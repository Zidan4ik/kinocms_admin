package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Banner;
import com.example.kinocms_admin.entity.BannerImage;
import com.example.kinocms_admin.repository.BannerImageRepository;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BannerImageServiceImpTest {
    @Mock
    private BannerImageRepository bannerImageRepository;
    @Mock
    private ImageServiceImp imageServiceImp;
    @InjectMocks
    private BannerImageServiceImp bannerImageServiceImp;
    private final static Long ID = 1L;
    private List<BannerImage> loadedBannersImages;

    @BeforeEach
    void setUp() {
        loadedBannersImages = TestDataUtil.loadBannerImage();
    }

    @Test
    void shouldSaveBannerImage() {
        bannerImageServiceImp.save(loadedBannersImages.get(0));
        verify(bannerImageRepository, times(1)).save(loadedBannersImages.get(0));
    }

    @Test
    void shouldSaveFile_WhenSuccessfully() {
        MultipartFile file = new MockMultipartFile("fileImage1", "image1.html", "text/html", "content".getBytes());
        when(bannerImageRepository.findById(ID)).thenReturn(Optional.of(loadedBannersImages.get(0)));
        when(imageServiceImp.generateFileName(file)).thenReturn("file image 1");
        bannerImageServiceImp.saveFile(loadedBannersImages.get(0), file);
    }

    @Test
    void shouldSaveFile_WhenThrowException() {
        MultipartFile file = new MockMultipartFile("fileImage1", "image1.html", "text/html", "content".getBytes());
        when(bannerImageRepository.findById(ID)).thenReturn(Optional.of(loadedBannersImages.get(0)));
        bannerImageServiceImp.saveFile(loadedBannersImages.get(0), file);
    }

    @Test
    void shouldSaveFile_WhenBannerImageIdIsNull() {
        BannerImage bannerImage = loadedBannersImages.get(0);
        bannerImage.setId(null);
        bannerImageServiceImp.saveFile(bannerImage, null);
    }

    @Test
    void shouldSaveFile_WhenOriginalNameIsEmpty() {
        MultipartFile file = new MockMultipartFile("fileImage1", "", "text/html", "content".getBytes());
        when(bannerImageRepository.findById(ID)).thenReturn(Optional.of(loadedBannersImages.get(0)));
        bannerImageServiceImp.saveFile(loadedBannersImages.get(0), file);
    }

    @Test
    void shouldSaveFile_WhenOriginalNameIsExist() {
        MultipartFile file = new MockMultipartFile("fileImage1", "exist", "text/html", "content".getBytes());
        when(bannerImageRepository.findById(ID)).thenReturn(Optional.of(loadedBannersImages.get(0)));
        bannerImageServiceImp.saveFile(loadedBannersImages.get(0), file);
    }

    @Test
    void shouldSaveFiles_WhenSuccessfully() {
        bannerImageServiceImp.saveFiles(loadedBannersImages, List.of(
                new MockMultipartFile("fileImage1", "exist", "text/html", "content".getBytes()),
                new MockMultipartFile("fileImage1", "exist", "text/html", "content".getBytes()),
                new MockMultipartFile("fileImage1", "exist", "text/html", "content".getBytes())
        ));
    }

    @Test
    void shouldSaveFiles_WhenDataAreNull() {
        bannerImageServiceImp.saveFiles(null, null);
    }

    @Test
    void shouldSaveFiles_WhenSizesAreNotMatch() {
        bannerImageServiceImp.saveFiles(loadedBannersImages, List.of(
                new MockMultipartFile("fileImage1", "exist", "text/html", "content".getBytes())
        ));
    }

    @Test
    void shouldDelete_ById() {
        bannerImageServiceImp.deleteById(ID);
        verify(bannerImageRepository, times(1)).deleteById(ID);
    }

    @Test
    void shouldGetAllBannersImages() {
        when(bannerImageRepository.findAll()).thenReturn(loadedBannersImages);
        List<BannerImage> bannersImages = bannerImageServiceImp.getAll();
        assertEquals(loadedBannersImages.size(),bannersImages.size(),"Sizes should be match");
    }

    @Test
    void shouldGetAllBannersImages_ByBanner() {
        Banner banner = new Banner();
        when(bannerImageRepository.getAllByBanner(banner)).thenReturn(loadedBannersImages);
        List<BannerImage> bannersImages = bannerImageServiceImp.getAllByBanner(banner);
        assertEquals(loadedBannersImages.size(),bannersImages.size(),"Sizes should be match");
    }
}