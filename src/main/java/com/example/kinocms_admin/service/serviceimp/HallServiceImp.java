package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.repository.HallRepository;
import com.example.kinocms_admin.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HallServiceImp implements HallService {
    private final HallRepository hallRepository;
    @Override
    public void save(Hall hall) {
        hallRepository.save(hall);
    }

    @Override
    public void deleteById(Long id) {
        hallRepository.deleteById(id);
    }

    @Override
    public List<Hall> getAll() {
        return hallRepository.findAll();
    }

    @Override
    public Optional<Hall> getById(Long id) {
        return hallRepository.findById(id);
    }
    public List<Hall> getAllByCinema(Cinema cinema){
        return hallRepository.getAllByCinema(cinema);
    }
}
