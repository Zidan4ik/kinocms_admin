package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Mark;
import com.example.kinocms_admin.repository.MarkRepository;
import com.example.kinocms_admin.service.MarkService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkServiceImp implements MarkService {
    private final MarkRepository markRepository;

    @Override
    public void save(Mark mark) {
        markRepository.save(mark);
    }

    @Override
    public void deleteById(long id) {
        markRepository.deleteById(id);
    }


}
