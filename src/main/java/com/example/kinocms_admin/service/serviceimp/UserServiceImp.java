package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.mapper.UserMapper;
import com.example.kinocms_admin.model.UserDTOView;
import com.example.kinocms_admin.repository.UserRepository;
import com.example.kinocms_admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<UserDTOView> searchByValueAndPagination(String value,Pageable pageable){
        List<String> userColumns = Arrays.asList("id","dateOfRegistration","dateOfBirthday","phone","email","name","lastName","nickname","city");
        GenericSearchSpecification<User> searchSpecification = new GenericSearchSpecification<>(userColumns,value);
        PageRequest modifyPageable = PageRequest.of(pageable.getPageNumber(), defaultPageSize);
        Page<User> page = userRepository.findAll(searchSpecification, modifyPageable);
        return page.map(UserMapper::toDTOView);
    }
    public Page<UserDTOView> getAll(Pageable pageable){
        Pageable modifyPageable = PageRequest.of(pageable.getPageNumber(), defaultPageSize);
        Page<User> page = userRepository.findAll(modifyPageable);
        return page.map(UserMapper::toDTOView);
    }
    public List<User> getAllSelected(){
        return userRepository.getAllByIsSelected(true);
    }
    public void saveAll(List<User> users){
        userRepository.saveAll(users);
    }
}
