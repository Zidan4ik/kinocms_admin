package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
class ImageServiceImpTest {
    @InjectMocks
    private ImageServiceImp imageServiceImp;
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        file = new MockMultipartFile("fileImage1", "image1.html", "text/html", "content".getBytes());
    }

    @Test
    void shouldSaveFile_WhenDataArePresent() throws IOException {
        imageServiceImp.saveFile(file, "logo name", GalleriesType.films, ImageType.image1, 1L);
    }

    @Test
    void shouldSaveFile_WhenFileIsNull() throws IOException {
        imageServiceImp.saveFile(null, null, null, null, null);
    }

    @Test
    void shouldSaveFile_WhenFileOriginalNameIsEmpty() throws IOException {
        file = new MockMultipartFile("fileImage1", "", "text/html", "content".getBytes());
        imageServiceImp.saveFile(file, null, null, null, null);
    }


    @Test
    void shouldSaveFiles_WhenDataArePresent() throws IOException {
        HashMap<String, MultipartFile> files = new HashMap<>();
        imageServiceImp.saveFiles(files, GalleriesType.films, 1L);
    }

    @Test
    void shouldFileName_WhenSuccessGenerate() {
        imageServiceImp.generateFileName(file);
    }

    @Test
    void shouldCreateGallery_WhenValueInFilesAreNotNull() {
        HashMap<String, MultipartFile> files = new HashMap<>();
        files.put("file1", file);
        imageServiceImp.createGallery(files, new Page(), GalleriesType.pages);
    }

    @Test
    void shouldCreateGallery_WhenValueInFilesAreNull() {
        HashMap<String, MultipartFile> files = new HashMap<>();
        files.put("file1", null);
        imageServiceImp.createGallery(files, new Page(), GalleriesType.pages);
    }

    @Test
    void shouldGenerateFilesMap_WhenFilesAreNull() {
        imageServiceImp.generateFilesMap(null);
    }

    @Test
    void shouldGenerateFilesMap_WhenFilesAreNotNull() {
        imageServiceImp.generateFilesMap(Collections.singletonList(file));
    }

    @Test
    void shouldGenerateFilesMap_WhenFileInFilesAreNull() {
        imageServiceImp.generateFilesMap(Collections.singletonList(null));
    }

    @Test
    void deleteFiles() {
        imageServiceImp.deleteFiles(GalleriesType.films, 1L);
    }
}