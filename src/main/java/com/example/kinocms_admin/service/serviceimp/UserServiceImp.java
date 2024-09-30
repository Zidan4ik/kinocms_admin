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
        LogUtil.logSaveNotification("user", "id", user.getId());
        userRepository.save(user);
        LogUtil.logSaveInfo("User", "id", user.getId());
    }

    @Override
    public void deleteById(Long id) {
        LogUtil.logDeleteNotification("user", "id", id);
        userRepository.deleteById(id);
        LogUtil.logDeleteInfo("User", "id", id);
    }

    @Override
    public Optional<User> getById(Long id) {
        LogUtil.logGetNotification("user", "id", id);
        Optional<User> userId = userRepository.findById(id);
        LogUtil.logGetInfo("User", "id", id, userId.isPresent());
        return userId;
    }

    @Override
    public List<User> getAll() {
        LogUtil.logGetAllNotification("users");
        List<User> users = userRepository.findAll();
        LogUtil.logSizeInfo("users", users.size());
        return users;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        LogUtil.logGetNotification("user", "email", email);
        Optional<User> userByEmail = userRepository.getByEmail(email);
        LogUtil.logGetInfo("User", "email", email, userByEmail.isPresent());
        return userByEmail;
    }

    @Override
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

    @Override
    public Page<UserDTOView> getAll(Pageable pageable) {
        LogUtil.logGetAllNotification("users", pageable);
        Pageable modifyPageable = PageRequest.of(pageable.getPageNumber(), defaultPageSize);
        Page<User> page = userRepository.findAll(modifyPageable);
        Page<UserDTOView> pageUserDTOView = page.map(UserMapper::toDTOView);
        LogUtil.logPageInfo(page, "Fetching all users");
        return pageUserDTOView;
    }
    @Override
    public List<User> getAllSelected() {
        LogUtil.logGetAllNotification("users", "selected state", true);
        List<User> selectedUsers = userRepository.getAllByIsSelected(true);
        selectedUsers.forEach(user -> {
            user.setSelected(false);
        });
        log.info("Successful changing all users in unselected state");
        return selectedUsers;
    }

    @Override
    public void saveAll(List<User> users) {
        LogUtil.logSaveAllNotifications("users");
        userRepository.saveAll(users);
        LogUtil.logSaveAllInfo("users");
    }

    @Override
    public List<CityCountDTO> findCityWithCounts() {
        log.info("Fetching all cities with count users there");
        List<Object[]> results = userRepository.findAllCitiesWithCountNative();
        List<CityCountDTO> citiesCount = results.stream()
                .map(result -> new CityCountDTO((String) result[0], ((Number) result[1]).longValue()))
                .toList();
        LogUtil.logSizeInfo("cities", citiesCount.size());
        return citiesCount;
    }

    @Override
    public Integer getCountGenders(boolean isMan) {
        log.info("Fetching count genders with state: {}", isMan);
        int size = userRepository.getAllByIsMan(isMan).size();
        LogUtil.logSizeInfo("genders", size);
        return size;
    }
}