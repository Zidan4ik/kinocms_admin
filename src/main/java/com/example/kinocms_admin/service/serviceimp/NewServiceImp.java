package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.New;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.repository.GalleryRepository;
import com.example.kinocms_admin.repository.MarkRepository;
import com.example.kinocms_admin.repository.NewRepository;
import com.example.kinocms_admin.service.NewService;
import com.example.kinocms_admin.util.HandleDataUtil;
import com.example.kinocms_admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NewServiceImp implements NewService{
    private final NewRepository newRepository;
    private final MarkServiceImp markServiceImp;
    @Override
    public void save(New newEntity) {
        newRepository.save(newEntity);
    }

    @Override
    public void saveNew(New newEntity, MultipartFile fileImage) {
        String uploadDir;
        String fileNameSchema = null;

        if (newEntity.getId() != null) {
            Optional<New> newBD = getById(newEntity.getId());
            newBD.ifPresent((object)-> newEntity.setNameImage(object.getNameImage()));
            newEntity.setPageTranslations(null);
            newEntity.setCeoBlocks(null);
        }
        if (fileImage != null) {
            fileNameSchema = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileImage.getOriginalFilename()));
            newEntity.setNameImage(fileNameSchema);
        }
        newEntity.setMarksList(HandleDataUtil.findSimilarMark(newEntity.getMarksList(),markServiceImp));
        save(newEntity);
        try {
            if (fileImage != null && !fileImage.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/news/image/" + newEntity.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileImage, fileNameSchema);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteById(Long id) {
        newRepository.deleteById(id);
    }
    @Override
    public List<New> getAll() {
        return newRepository.findAll();
    }
    @Override
    public Optional<New> getById(Long id) {
        return newRepository.findById(id);
    }
    public Integer getAmountNews(){
        return newRepository.findAll().size();
    }
}
