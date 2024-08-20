package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.mapper.UserMapper;
import com.example.kinocms_admin.model.CityCountDTO;
import com.example.kinocms_admin.model.UserDTOView;
import com.example.kinocms_admin.repository.UserRepository;
import com.example.kinocms_admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private static final int defaultPageSize = 2;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Page<UserDTOView> filtrationAndSortingAndPagination(Pageable pageable,
                                                               String searchValue) {
        List<String> userColumns = Arrays.asList("id", "dateOfRegistration", "dateOfBirthday", "phone", "email", "name", "lastName", "nickname", "city");
        GenericSearchSpecification<User> searchSpecification = new GenericSearchSpecification<>(userColumns, searchValue);
        PageRequest modifyPageable = PageRequest.of(pageable.getPageNumber(), defaultPageSize, pageable.getSort());
        Page<User> page = userRepository.findAll(searchSpecification, modifyPageable);
        return page.map(UserMapper::toDTOView);
    }

    public Page<UserDTOView> getAll(Pageable pageable) {
        Pageable modifyPageable = PageRequest.of(pageable.getPageNumber(), defaultPageSize);
        Page<User> page = userRepository.findAll(modifyPageable);
        return page.map(UserMapper::toDTOView);
    }

    public List<User> getAllSelected() {
        List<User> selectedUsers = userRepository.getAllByIsSelected(true);
        selectedUsers.forEach(user -> {
            user.setSelected(false);
        });
        return selectedUsers;
    }

    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    public List<CityCountDTO> findCityWithCounts() {
        List<Object[]> results = userRepository.findAllCitiesWithCountNative();
        return results.stream()
                .map(result -> new CityCountDTO((String) result[0], ((Number) result[1]).longValue()))
                .toList();
    }

    public Integer getCountGenders(boolean isMan) {
        return userRepository.getAllByIsMan(isMan).size();
    }
}