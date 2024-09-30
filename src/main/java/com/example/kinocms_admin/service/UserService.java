package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.model.CityCountDTO;
import com.example.kinocms_admin.model.UserDTOView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    void deleteById(Long id);
    Optional<User> getById(Long id);
    List<User> getAll();
    Optional<User> getByEmail(String email);
    Page<UserDTOView> filtrationAndSortingAndPagination(Pageable pageable, String searchValue);
    Page<UserDTOView> getAll(Pageable pageable);
    List<User> getAllSelected();
    void saveAll(List<User> users);
    List<CityCountDTO> findCityWithCounts();
    Integer getCountGenders(boolean isMan);
}
