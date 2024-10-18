package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.New;
import com.example.kinocms_admin.repository.NewRepository;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NewServiceImpTest {
    @Mock
    private NewRepository newRepository;
    @Mock
    private ImageUtil imageUtil;
    @InjectMocks
    private NewServiceImp newServiceImp;
    private List<New> loadedNews;
    private final static Long ID = 1L;
    @BeforeEach
    void setUp() {
        loadedNews = TestDataUtil.loadNews();
    }

    @Test
    void save() {
        newRepository.save(loadedNews.get(0));
        verify(newRepository,times(1)).save(loadedNews.get(0));
    }

    @Test
    void shouldSaveNew_WhenDataArePresent() {
        MultipartFile file = new MockMultipartFile("image", "new.html", "text/html", "content".getBytes());
        newServiceImp.saveNew(loadedNews.get(0),file);
    }
    @Test
    void shouldSaveNew_WhenDataAreNull() {
        New new_ = loadedNews.get(1);
        newServiceImp.saveNew(new_,isNull());
    }
    @Test
    void shouldSaveNew_WhenFileOriginalNameIsEmpty() {
        New new_ = loadedNews.get(1);
        MultipartFile file = new MockMultipartFile("image", "", "text/html", "content".getBytes());
        newServiceImp.saveNew(new_,file);
    }
    @Test
    void shouldSaveNew_WhenThrowExceptionIO() {
        MultipartFile file = new MockMultipartFile("image", "new.html", "text/html", "content".getBytes());
        try (MockedStatic<ImageUtil> mockStatic = mockStatic(ImageUtil.class)) {
            mockStatic.when(() -> ImageUtil.saveAfterDelete(anyString(), any(MultipartFile.class), anyString()))
                    .thenThrow(IOException.class);
            newServiceImp.saveNew(loadedNews.get(0),file);
        }
    }

    @Test
    void shouldDeleteNew_ById() {
        newServiceImp.deleteById(ID);
        verify(newRepository,times(1)).deleteById(ID);
    }

    @Test
    void shouldGetAll() {
        when(newRepository.findAll()).thenReturn(loadedNews);
        List<New> news = newServiceImp.getAll();
        assertEquals(loadedNews.size(),news.size(),"Size should be match");
    }

    @Test
    void shouldGetById() {
        when(newRepository.findById(ID)).thenReturn(Optional.of(loadedNews.get(0)));
        Optional<New> newById = newServiceImp.getById(ID);
        assertTrue(newById.isPresent(),"New was not found");
        assertEquals(ID,newById.get().getId(),"Ids should be match");
    }
    @Test
    void shouldDelete_ById_WhenPresent(){
        when(newRepository.findById(ID)).thenReturn(Optional.of(loadedNews.get(0)));
        newServiceImp.deleteById(ID);
        verify(newRepository,times(1)).deleteById(ID);
    }
}