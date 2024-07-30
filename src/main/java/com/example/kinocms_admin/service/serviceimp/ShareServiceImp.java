package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Share;
import com.example.kinocms_admin.repository.ShareRepository;
import com.example.kinocms_admin.service.ShareService;
import com.example.kinocms_admin.util.HandleDataUtil;
import com.example.kinocms_admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShareServiceImp implements ShareService {

    private final ShareRepository shareRepository;
    private final MarkServiceImp markServiceImp;

    @Override
    public void save(Share share) {
        shareRepository.save(share);
    }

    @Override
    public void saveShare(Share share, MultipartFile fileImage, MultipartFile fileBanner) {
        String uploadDir;
        String fileNameSchema = null;
        String fileNameBanner = null;

        if (share.getId() != null) {
            Optional<Share> newBD = getById(share.getId());
            newBD.ifPresent((object) -> share.setNameImage(object.getNameImage()));
            newBD.ifPresent((object) -> share.setNameBanner(object.getNameBanner()));
            share.setPageTranslations(null);
            share.setCeoBlocks(null);
        }
        if (fileImage != null) {
            fileNameSchema = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileImage.getOriginalFilename()));
            share.setNameImage(fileNameSchema);
        }
        if (fileBanner != null) {
            fileNameBanner = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(fileBanner.getOriginalFilename()));
            share.setNameBanner(fileNameBanner);
        }
        share.setMarksList(HandleDataUtil.findSimilarMark(share.getMarksList(), markServiceImp));
        save(share);
        try {
            if (fileImage != null && !fileImage.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/shares/image/" + share.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileImage, fileNameSchema);
            }
            if (fileBanner != null && !fileBanner.getOriginalFilename().isEmpty()) {
                uploadDir = "./uploads/shares/banner/" + share.getId();
                ImageUtil.saveAfterDelete(uploadDir, fileBanner, fileNameBanner);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        shareRepository.deleteById(id);
    }

    @Override
    public List<Share> getAll() {
        return shareRepository.findAll();
    }

    @Override
    public Optional<Share> getById(Long id) {
        return shareRepository.findById(id);
    }
}