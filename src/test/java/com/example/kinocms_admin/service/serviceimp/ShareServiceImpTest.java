package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Mark;
import com.example.kinocms_admin.entity.Share;
import com.example.kinocms_admin.repository.MarkRepository;
import com.example.kinocms_admin.repository.ShareRepository;
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
class ShareServiceImpTest {
    @Mock
    private ShareRepository shareRepository;
    @Mock
    private MarkRepository markRepository;
    @Mock
    private MarkServiceImp markServiceImp;
    @InjectMocks
    private ShareServiceImp shareService;
    private final static Long ID = 1L;
    private List<Share> loadedShares;
    private Share share;
    private MockMultipartFile fileImage;
    private MockMultipartFile fileBanner;

    @BeforeEach
    void setUp() {
        loadedShares = TestDataUtil.loadShares();
        share = loadedShares.get(0);
        fileImage = new MockMultipartFile("fileImage", "share1.html", "text/html", "content".getBytes());
        fileBanner = new MockMultipartFile("fileBanner", "share2.html", "text/html", "content".getBytes());
    }

    @Test
    void save() {
        shareService.save(share);
        verify(shareRepository, times(1)).save(share);
    }

    @Test
    void shouldSaveShare_WhenFilesAreProvided() {
        when(shareRepository.findById(ID)).thenReturn(Optional.of(loadedShares.get(1)));
        when(markServiceImp.getByName(anyString())).thenReturn(Optional.of(new Mark("TestMark")));
        shareService.saveShare(share, fileImage, fileBanner);
        verify(shareRepository, times(1)).findById(ID);
    }

    @Test
    void shouldSaveShare_WhenDataAreNull() {
        this.share.setId(null);
        when(markServiceImp.getByName(anyString())).thenReturn(Optional.of(new Mark("TestMark")));
        shareService.saveShare(share, null, null);
        verify(markServiceImp, times(3)).getByName(anyString());
    }

    @Test
    void shouldSaveShare_WhenFilesAreEmpty() {
        this.share.setId(null);
        fileImage = new MockMultipartFile("fileImage", "", "text/html", "content".getBytes());
        fileBanner = new MockMultipartFile("fileBanner", "", "text/html", "content".getBytes());

        when(markServiceImp.getByName(anyString())).thenReturn(Optional.of(new Mark("TestMark")));
        shareService.saveShare(share, fileImage, fileBanner);
        verify(markServiceImp, times(3)).getByName(anyString());
    }

    @Test
    void shouldSaveShare_WhenThrowException() {
        when(shareRepository.findById(ID)).thenReturn(Optional.of(loadedShares.get(0)));
        when(markServiceImp.getByName(anyString())).thenReturn(Optional.of(new Mark("TestMark")));
        try (MockedStatic<ImageUtil> mockedImageUtil = mockStatic(ImageUtil.class)) {
            mockedImageUtil.when(() -> ImageUtil.saveAfterDelete(anyString(), any(MultipartFile.class), anyString()))
                    .thenThrow(IOException.class);
            shareService.saveShare(share, fileImage, fileBanner);
            mockedImageUtil.verify(() -> ImageUtil.saveAfterDelete(anyString(), eq(fileImage), anyString()), times(1));
        }
        shareService.saveShare(share, fileImage, fileBanner);
        verify(shareRepository, times(2)).findById(ID);
    }

    @Test
    void deleteById() {
        shareService.deleteById(ID);
        verify(shareRepository, times(1)).deleteById(ID);
    }

    @Test
    void getAll() {
        when(shareRepository.findAll()).thenReturn(loadedShares);
        List<Share> shares = shareService.getAll();
        assertEquals(loadedShares.size(), shares.size(), "Sizes should be match");
        verify(shareRepository, times(1)).findAll();
    }

    @Test
    void getById() {
        when(shareRepository.findById(ID)).thenReturn(Optional.of(share));
        Optional<Share> shareById = shareService.getById(ID);
        assertTrue(shareById.isPresent(), "Share was not found");
        assertEquals(ID, shareById.get().getId(), "Ids should be match");
        verify(shareRepository, times(1)).findById(ID);
    }

    @Test
    void getAmountShares() {
        when(shareRepository.findAll()).thenReturn(loadedShares);
        Integer size = shareService.getAmountShares();
        assertEquals(loadedShares.size(), size, "Sizes should be match");
        verify(shareRepository, times(1)).findAll();
    }
}