package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    void deleteById(Long id);
    Optional<User> getById(Long id);
    List<User> getAll();
    Optional<User> getByEmail(String email);
}
