package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import com.example.kinocms_admin.enums.PageType;
import com.example.kinocms_admin.repository.PageRepository;
import com.example.kinocms_admin.service.PageService;
import com.example.kinocms_admin.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageServiceImp implements PageService {
    private final PageRepository pageRepository;
    private final GalleryServiceImp galleryServiceImp;
    private final ImageServiceImp imageServiceImp;

    @Override
    public void save(Page page) {
        LogUtil.logSaveNotification("page", "id", page.getId());
        pageRepository.save(page);
        LogUtil.logSaveNotification("Page", "id", page.getId());
    }

    @Override
    public void saveImages(Page page, MultipartFile fileBanner, MultipartFile fileImage1, MultipartFile fileImage2, MultipartFile fileImage3, List<MultipartFile> filesGalleries) {
        String nameBanner = null;
        String nameImage1 = null;
        String nameImage2 = null;
        String nameImage3 = null;
        HashMap<String, MultipartFile> mapFile = new HashMap<>();
        if (page.getId() != null) {
            Optional<Page> pageById = getById(page.getId());
            updatePageAttributes(page, pageById);
        }
        if (fileBanner != null) {
            nameBanner = imageServiceImp.generateFileName(fileBanner);
            page.setNameBanner(nameBanner);
        }
        if (fileImage1 != null) {
            nameImage1 = imageServiceImp.generateFileName(fileImage1);
            page.setNameImage1(nameImage1);
        }
        if (fileImage2 != null) {
            nameImage2 = imageServiceImp.generateFileName(fileImage2);
            page.setNameImage2(nameImage2);
        }
        if (fileImage3 != null) {
            nameImage3 = imageServiceImp.generateFileName(fileImage3);
            page.setNameImage3(nameImage3);
        }
        List<Gallery> galleriesRes = imageServiceImp.createGallery(filesGalleries, page, GalleriesType.pages, mapFile);
        page.setGalleries(galleriesRes);
        save(page);
        try {
            if (fileBanner != null && !Objects.requireNonNull(fileBanner.getOriginalFilename()).isEmpty()) {
                imageServiceImp.saveFile(fileBanner, nameBanner, GalleriesType.pages, ImageType.banner, page.getId());
            }
            if (fileImage1 != null && !Objects.requireNonNull(fileImage1.getOriginalFilename()).isEmpty()) {
                imageServiceImp.saveFile(fileImage1, nameImage1, GalleriesType.pages, ImageType.image1, page.getId());
            }
            if (fileImage2 != null && !Objects.requireNonNull(fileImage2.getOriginalFilename()).isEmpty()) {
                imageServiceImp.saveFile(fileImage2, nameImage2, GalleriesType.pages, ImageType.image2, page.getId());
            }
            if (fileImage3 != null && !Objects.requireNonNull(fileImage3.getOriginalFilename()).isEmpty()) {
                imageServiceImp.saveFile(fileImage3, nameImage3, GalleriesType.pages, ImageType.image3, page.getId());
            }
            if (!mapFile.isEmpty()) {
                imageServiceImp.saveFiles(filesGalleries, mapFile, GalleriesType.pages, page.getId());
            }
        } catch (IOException e) {
            LogUtil.logErrorSavingFiles(e);
        }
    }

    @Override
    public Optional<Page> getById(Long id) {
        LogUtil.logGetNotification("page", "id", id);
        Optional<Page> pageById = pageRepository.findById(id);
        LogUtil.logGetInfo("Page", "id", id, pageById.isPresent());
        return pageById;
    }

    @Override
    public void deleteById(Long id) {
        LogUtil.logDeleteNotification("page", "id", id);
        pageRepository.deleteById(id);
        LogUtil.logDeleteInfo("Page", "id", id);
    }

    @Override
    public List<Page> getAll() {
        LogUtil.logGetAllNotification("pages");
        List<Page> pages = pageRepository.findAll();
        LogUtil.logSizeInfo("pages", pages.size());
        return pages;
    }

    @Override
    public List<Page> getAllByPageType(PageType type) {
        LogUtil.logGetAllNotification("pages", "type", type);
        List<Page> pages = pageRepository.getAllByType(type);
        LogUtil.logSizeInfo("pages", pages.size());
        return pages;
    }

    @Override
    public void updatePageAttributes(Page page, Optional<Page> pageById) {
        LogUtil.logUpdateNotifications("page","id",page.getId());
        pageById.ifPresent(p -> page.setNameBanner(p.getNameBanner()));
        pageById.ifPresent(p -> page.setNameImage1(p.getNameImage1()));
        pageById.ifPresent(p -> page.setNameImage2(p.getNameImage2()));
        pageById.ifPresent(p -> page.setNameImage3(p.getNameImage3()));
        pageById.ifPresent(p -> page.setGalleries(galleryServiceImp.getAllByPage(p)));
        pageById.ifPresent(p -> page.setType(p.getType()));
        page.setPageTranslations(null);
        page.setCeoBlocks(null);
        LogUtil.logUpdateInfo("Page", "id", page.getId());
    }
}
