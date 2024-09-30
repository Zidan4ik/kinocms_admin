package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.PageType;
import com.example.kinocms_admin.repository.PageRepository;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PageServiceImpTest {
    @Mock
    private PageRepository pageRepository;
    @Mock
    private GalleryServiceImp galleryService;
    @Mock
    private ImageServiceImp imageService;
    @InjectMocks
    private PageServiceImp pageService;
    private List<Page> loadedPages;
    private MultipartFile fileImage1;
    private MultipartFile fileImage2;
    private MultipartFile fileImage3;
    private MultipartFile fileBanner;
    private final static Long ID = 1L;

    @BeforeEach
    void setUp() {
        loadedPages = TestDataUtil.loadPages();
        fileImage1 = new MockMultipartFile("fileImage1", "image1.html", "text/html", "content".getBytes());
        fileImage2 = new MockMultipartFile("fileImage2", "image2.html", "text/html", "content".getBytes());
        fileImage3 = new MockMultipartFile("fileImage3", "image3.html", "text/html", "content".getBytes());
        fileBanner = new MockMultipartFile("fileImage4", "banner.html", "text/html", "content".getBytes());
    }

    @Test
    void shouldSave() {
        pageRepository.save(loadedPages.get(0));
        verify(pageRepository, times(1)).save(loadedPages.get(0));
    }

    @Test
    void shouldSaveImages_WhenFilesAreNull() {
        Page page = loadedPages.get(0);
        page.setId(null);
        when(imageService.createGallery(any(), eq(page), eq(GalleriesType.pages)))
                .thenReturn(new ArrayList<>());
        pageService.saveImages(page, null, null, null, null, new ArrayList<>());
    }

    @Test
    void shouldSaveImages_WhenFilesArePresent() {
        Page page = loadedPages.get(0);
        List<MultipartFile> filesGalleries = List.of(fileImage1, fileImage2, fileImage3);
        HashMap<String, MultipartFile> map = new HashMap<>();
        map.put("image1",fileImage1);
        map.put("image2",fileImage2);
        map.put("image3",fileImage3);

        when(imageService.createGallery(any(), eq(page), eq(GalleriesType.pages)))
                .thenReturn(Arrays.asList(new Gallery(),new Gallery()));
        when(pageRepository.findById(page.getId())).thenReturn(Optional.of(page));
        when(imageService.generateFilesMap(filesGalleries)).thenReturn(map);
        pageService.saveImages(page, fileBanner, fileImage1, fileImage2, fileImage3, filesGalleries);
        verify(pageRepository,times(1)).findById(page.getId());
    }

    @Test
    void shouldSaveImages_WhenFilesAreEmpty() {
        Page page = loadedPages.get(0);
        when(imageService.createGallery(any(), eq(page), eq(GalleriesType.pages)))
                .thenReturn(new ArrayList<>());
        when(pageRepository.findById(page.getId())).thenReturn(Optional.of(page));
        fileImage1 = new MockMultipartFile("fileImage1", "", "text/html", "content".getBytes());
        fileImage2 = new MockMultipartFile("fileImage2", "", "text/html", "content".getBytes());
        fileImage3 = new MockMultipartFile("fileImage3", "", "text/html", "content".getBytes());
        fileBanner = new MockMultipartFile("fileImage4", "", "text/html", "content".getBytes());
        pageService.saveImages(page, fileBanner, fileImage1, fileImage2, fileImage3, new ArrayList<>());
        verify(pageRepository,times(1)).findById(page.getId());
    }

    @Test
    void shouldSaveImages_WhenThrowException() {
        Page page = loadedPages.get(0);
        List<MultipartFile> filesGalleries = List.of(fileImage1, fileImage2, fileImage3);
        doThrow(new RuntimeException("Error saving file")).when(pageRepository).save(any(Page.class));
        assertThrows(RuntimeException.class, () -> {
            pageService.saveImages(page, fileBanner, fileImage1, fileImage2, fileImage3, filesGalleries);
        });
        verify(pageRepository,times(1)).save(any(Page.class));
    }

    @Test
    void shouldGetById() {
        when(pageRepository.findById(ID)).thenReturn(Optional.of(loadedPages.get(0)));
        Optional<Page> pageById = pageService.getById(ID);
        assertTrue(pageById.isPresent(),"Page was not found");
        assertEquals(ID,pageById.get().getId(),"Ids should be match");
        verify(pageRepository,times(1)).findById(ID);
    }

    @Test
    void shouldDeleteById() {
        pageService.deleteById(ID);
        verify(pageRepository,times(1)).deleteById(ID);
    }

    @Test
    void shouldGetAll() {
        when(pageRepository.findAll()).thenReturn(loadedPages);
        List<Page> pages = pageService.getAll();
        assertEquals(loadedPages.size(),pages.size(),"Size should be match");
        verify(pageRepository,times(1)).findAll();
    }

    @Test
    void shouldGetAllByPage() {
        PageType expectedType = PageType.film;
        List<Page> expectedPages = loadedPages.stream()
                .filter(p -> p.getType().equals(expectedType))
                .toList();
        when(pageRepository.getAllByType(expectedType)).thenReturn(expectedPages);
        List<Page> pagesByType = pageService.getAllByPageType(expectedType);
        assertEquals(expectedPages.size(),pagesByType.size(),"Size should be match");
        verify(pageRepository,times(1)).getAllByType(expectedType);
    }
}