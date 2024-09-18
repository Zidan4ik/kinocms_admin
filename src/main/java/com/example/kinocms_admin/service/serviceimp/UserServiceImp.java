package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.mapper.UserMapper;
import com.example.kinocms_admin.model.CityCountDTO;
import com.example.kinocms_admin.model.UserDTOView;
import com.example.kinocms_admin.repository.UserRepository;
import com.example.kinocms_admin.service.UserService;
import com.example.kinocms_admin.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private static final int defaultPageSize = 2;

    @Override
    public void save(User user) {
        log.info("Saving user!");
        userRepository.save(user);
        log.info("User saved with name: {}", user);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting user with id!");
        userRepository.deleteById(id);
        log.info("User with id: {} deleted", id);
    }

    @Override
    public Optional<User> getById(Long id) {
        log.info("Fetching user with id");
        Optional<User> userId = userRepository.findById(id);
        if (userId.isPresent()) {
            log.info("User with id: {} was found", id);
        } else {
            log.warn("User with id: {} wasn't found", id);
        }
        return userId;
    }

    @Override
    public List<User> getAll() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        log.info("Total users found: {}", users.size());
        return users;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        log.info("Fetching user with email");
        Optional<User> userByEmail = userRepository.getByEmail(email);
        if (userByEmail.isPresent()) {
            log.info("User with email: {} was found", email);
        } else {
            log.warn("User is not exist with email: {}", email);
        }
        return userByEmail;
    }

    public Page<UserDTOView> filtrationAndSortingAndPagination(Pageable pageable, String searchValue) {
        log.info("Filtration&sorting&pagination with searchValue: {} and pageable: {}", searchValue, pageable);
        List<String> userColumns = Arrays.asList("id", "dateOfRegistration", "dateOfBirthday", "phone", "email", "name", "lastName", "nickname", "city");
        GenericSearchSpecification<User> searchSpecification = new GenericSearchSpecification<>(userColumns, searchValue);
        PageRequest modifyPageable = PageRequest.of(pageable.getPageNumber(), defaultPageSize, pageable.getSort());
        log.debug("Modified PageAble: {}", modifyPageable);
        Page<User> page = userRepository.findAll(searchSpecification, modifyPageable);
        log.info("Found {} users after filtration", page.getTotalElements());
        Page<UserDTOView> userDTOSRes = page.map(UserMapper::toDTOView);
        LogUtil.logPageInfo(page, "Filtration & Sorting & Pagination");
        return userDTOSRes;
    }

    public Page<UserDTOView> getAll(Pageable pageable) {
        log.info("Fetching all users with pageable: {}", pageable);
        Pageable modifyPageable = PageRequest.of(pageable.getPageNumber(), defaultPageSize);
        Page<User> page = userRepository.findAll(modifyPageable);
        Page<UserDTOView> pageUserDTOView = page.map(UserMapper::toDTOView);
        LogUtil.logPageInfo(page, "Fetching all users");
        return pageUserDTOView;
    }

    public List<User> getAllSelected() {
        log.info("Fetch all users with selected state");
        List<User> selectedUsers = userRepository.getAllByIsSelected(true);
        selectedUsers.forEach(user -> {
            user.setSelected(false);
        });
        log.info("Successful changing all users in unselected state");
        return selectedUsers;
    }

    public void saveAll(List<User> users) {
        log.info("Saving all users");
        userRepository.saveAll(users);
        log.info("All users were success saved");
    }

    public List<CityCountDTO> findCityWithCounts() {
        log.info("Fetching all cities with count users there");
        List<Object[]> results = userRepository.findAllCitiesWithCountNative();
        List<CityCountDTO> citiesCount = results.stream()
                .map(result -> new CityCountDTO((String) result[0], ((Number) result[1]).longValue()))
                .toList();
        log.info("Found cities: {}", citiesCount.size());
        return citiesCount;
    }

    public Integer getCountGenders(boolean isMan) {
        log.info("Fetching count genders with state: {}", isMan);
        int size = userRepository.getAllByIsMan(isMan).size();
        log.info("Found genders: {}", size);
        return size;
    }
}