package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Banner;
import com.example.kinocms_admin.repository.BannerRepository;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BannerServiceImpTest {
    @Mock
    private BannerRepository bannerRepository;
    @InjectMocks
    private BannerServiceImp bannerServiceImp;
    private List<Banner> loadedBanners;
    private final static Long ID = 1L;
    @BeforeEach
    void setUp() {
        loadedBanners = TestDataUtil.loadBanner();
    }

    @Test
    void shouldSaveBanner() {
        bannerServiceImp.save(loadedBanners.get(0));
        verify(bannerRepository,times(1)).save(loadedBanners.get(0));
    }

    @Test
    void shouldDeleteBanner_ById() {
        bannerServiceImp.deleteById(ID);
        verify(bannerRepository,times(1)).deleteById(ID);
    }

    @Test
    void shouldGetBanner_ById() {
        when(bannerRepository.findById(ID)).thenReturn(Optional.of(loadedBanners.get(0)));
        Optional<Banner> bannerById = bannerServiceImp.getById(ID);
        assertTrue(bannerById.isPresent(),"Banner was not found");
        assertEquals(ID,bannerById.get().getId(),"Ids should be match");
    }

    @Test
    void shouldGetAllBanners() {
        when(bannerRepository.findAll()).thenReturn(loadedBanners);
        List<Banner> banners = bannerServiceImp.getAll();
        assertEquals(loadedBanners.size(),banners.size(),"Sizes should be match");
    }
}