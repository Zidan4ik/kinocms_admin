package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.PageType;
import com.example.kinocms_admin.repository.PageRepository;
import com.example.kinocms_admin.service.PageService;
import com.example.kinocms_admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PageServiceImp implements PageService {
    private final PageRepository pageRepository;
    private final GalleryServiceImp galleryServiceImp;

    @Override
    public void save(Page page) {
        pageRepository.save(page);
    }

    @Override
    public void saveImages(Page page, MultipartFile fileBanner, MultipartFile fileImage1, MultipartFile fileImage2, MultipartFile fileImage3, List<MultipartFile> galleries) {
        String uploadDir;
        String nameBanner = null;
        String nameImage1 = null;
        String nameImage2 = null;
        String nameImage3 = null;
        HashMap<String, MultipartFile> mapFile = new HashMap<>();
        List<Gallery> galleriesRes = new ArrayList<>();
        if (page.getId() != null) {
            Optional<Page> pageById = pageRepository.findById(page.getId());
            pageById.ifPresent(p -> page.setNameBanner(p.getNameBanner()));
            pageById.ifPresent(p -> page.setNameImage1(p.getNameImage1()));
            pageById.ifPresent(p -> page.setNameImage2(p.getNameImage2()));
            pageById.ifPresent(p -> page.setNameImage3(p.getNameImage3()));
            pageById.ifPresent(p -> page.setGalleries(galleryServiceImp.getAllByPage(p)));
            pageById.ifPresent(p->page.setType(p.getType()));
            page.setPageTranslations(null);
            page.setCeoBlocks(null);
        }else{
            page.setType(PageType.additional);
        }
        if (fileBanner != null) {
            nameBanner = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileBanner.getOriginalFilename()));
            page.setNameBanner(nameBanner);
        }
        if (fileImage1 != null) {
            nameImage1 = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileImage1.getOriginalFilename()));
            page.setNameImage1(nameImage1);
        }
        if (fileImage2 != null) {
            nameImage2 = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileImage2.getOriginalFilename()));
            page.setNameImage2(nameImage2);
        }
        if (fileImage3 != null) {
            nameImage3 = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileImage3.getOriginalFilename()));
            page.setNameImage3(nameImage3);
        }

        if (galleries != null) {
            for (MultipartFile fileGallery : galleries) {
                if (fileGallery != null) {
                    String name = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileGallery.getOriginalFilename()));
                    galleriesRes.add(new Gallery(name, GalleriesType.halls, page));
                    mapFile.put(name, fileGallery);
                }
            }
        }
        page.setGalleries(galleriesRes);
        save(page);

        try {
            if (fileBanner != null && !fileBanner.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/pages/banner/" + page.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileBanner, nameBanner);
            }
            if (fileImage1 != null && !fileImage1.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/pages/image1/" + page.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileImage1, nameImage1);
            }
            if (fileImage2 != null && !fileImage2.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/pages/image2/" + page.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileImage2, nameImage2);
            }
            if (fileImage3 != null && !fileImage3.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/pages/image3/" + page.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileImage3, nameImage3);
            }
            if (!mapFile.isEmpty()) {
                uploadDir = "./uploads/pages/galleries/" + page.getId();
                ImageUtil.savesAfterDelete(uploadDir, mapFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Page> getById(Long id) {
        return pageRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        pageRepository.deleteById(id);
    }

    @Override
    public List<Page> getAll() {
        return pageRepository.findAll();
    }

    @Override
    public List<Page> getAllByPageType(PageType type) {
        return pageRepository.getAllByType(type);
    }
}
