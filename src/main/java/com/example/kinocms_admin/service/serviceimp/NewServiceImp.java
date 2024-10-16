package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.New;
import com.example.kinocms_admin.repository.NewRepository;
import com.example.kinocms_admin.service.NewService;
import com.example.kinocms_admin.util.HandleDataUtil;
import com.example.kinocms_admin.util.ImageUtil;
import com.example.kinocms_admin.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewServiceImp implements NewService {
    private final NewRepository newRepository;
    private final MarkServiceImp markServiceImp;

    @Override
    public void save(New newEntity) {
        LogUtil.logSaveNotification("new", "id", newEntity.getId());
        newRepository.save(newEntity);
        LogUtil.logSaveInfo("new", "id", newEntity.getId());
    }

    @Override
    public void saveNew(New newEntity, MultipartFile fileImage) {
        String uploadDir;
        String fileNameSchema = null;
        if (newEntity.getId() != null) {
            Optional<New> newBD = getById(newEntity.getId());
            newBD.ifPresent((object) -> newEntity.setNameImage(object.getNameImage()));
            newEntity.setPageTranslations(null);
            newEntity.setCeoBlocks(null);
        }
        if (fileImage != null) {
            fileNameSchema = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileImage.getOriginalFilename()));
            newEntity.setNameImage(fileNameSchema);
        }
        newEntity.setMarksList(HandleDataUtil.findSimilarMark(newEntity.getMarksList(), markServiceImp));
        save(newEntity);
        try {
            if (fileImage != null && !fileImage.getOriginalFilename().isEmpty()) {
                uploadDir = "/home/slj/projects/KinoCMS-R.Pravnyk/uploads/news/image/" + newEntity.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileImage, fileNameSchema);
            }
        } catch (IOException e) {
            LogUtil.logErrorSavingFiles(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        LogUtil.logDeleteNotification("new", "id", id);
        Optional<New> newId = getById(id);
        if(newId.isPresent()){
            newId.get().getMarksList().clear();;
            save(newId.get());
        }
        newRepository.deleteById(id);
        LogUtil.logDeleteInfo("New", "id", id);
    }

    @Override
    public List<New> getAll() {
        LogUtil.logGetAllNotification("news");
        List<New> news = newRepository.findAll();
        LogUtil.logSizeInfo("news", news.size());
        return news;
    }

    @Override
    public Optional<New> getById(Long id) {
        LogUtil.logGetNotification("new", "id", id);
        Optional<New> newId = newRepository.findById(id);
        LogUtil.logGetInfo("New", "id", id, newId.isPresent());
        return newId;
    }
}
