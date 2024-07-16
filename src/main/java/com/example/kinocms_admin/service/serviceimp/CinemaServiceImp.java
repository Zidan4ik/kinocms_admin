package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.repository.CinemaRepository;
import com.example.kinocms_admin.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CinemaServiceImp implements CinemaService {
    private final CinemaRepository cinemaRepository;
    @Override
    public void save(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    @Override
    public void deleteById(Long id) {
        cinemaRepository.deleteById(id);
    }

    @Override
    public List<Cinema> getAll() {
        return cinemaRepository.findAll();
    }

    @Override
    public Optional<Cinema> getById(Long id) {
        return cinemaRepository.findById(id);
    }
}
