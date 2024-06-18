package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.repository.CeoBlockRepository;
import com.example.kinocms_admin.service.CeoBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CeoBlockServiceImp implements CeoBlockService {
    private final CeoBlockRepository ceoBlockRepository;
    @Override
    public void save(CeoBlock ceoBlock) {
    ceoBlockRepository.save(ceoBlock);
    }

    @Override
    public void deleteById(long id) {
    ceoBlockRepository.deleteById(id);
    }

    @Override
    public Optional<CeoBlock> getById(long id) {
        return ceoBlockRepository.findById(id);
    }
}
