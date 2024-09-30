package com.example.kinocms_admin.service.serviceimp;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.mapper.UserMapper;
import com.example.kinocms_admin.model.CityCountDTO;
import com.example.kinocms_admin.model.UserDTOView;
import com.example.kinocms_admin.repository.UserRepository;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImp userService;
    private List<User> loadedUsers;

    @BeforeEach
    void setUp() {
        loadedUsers = TestDataUtil.loadUsers();
    }

    @Test
    void shouldSave() {
        User user = loadedUsers.get(0);
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.save(user);
        Optional<User> userById = userService.getById(user.getId());
        assertTrue(userById.isPresent(), "User should be present");
        assertEquals(user.getId(), userById.get().getId(), "Users should match between");
        verify(userRepository,timeout(1)).findById(1L);
    }

    @Test
    void shouldDelete_ById() {
        Long expectedId = 1L;
        userService.deleteById(expectedId);
        verify(userRepository,timeout(1)).deleteById(expectedId);
    }

    @Test
    void shouldGet_ById() {
        Long expectedId = 1L;
        when(userRepository.findById(expectedId)).thenReturn(Optional.ofNullable(loadedUsers.get(0)));
        Optional<User> userById = userService.getById(expectedId);
        assertTrue(userById.isPresent(),"User should be present");
        assertEquals(expectedId,userById.get().getId(),"User id should be match");
        verify(userRepository,timeout(1)).findById(expectedId);
    }

    @Test
    void shouldGetAll() {
        when(userRepository.findAll()).thenReturn(loadedUsers);
        List<User> users = userService.getAll();
        assertEquals(loadedUsers.size(),users.size(),"Size should be match");
        verify(userRepository,timeout(1)).findAll();
    }

    @Test
    void shouldGet_ByEmail() {
        String expectedEmail = "romich20031@gmail.com";
        when(userRepository.getByEmail(expectedEmail)).thenReturn(Optional.ofNullable(loadedUsers.get(1)));
        Optional<User> userByEmail = userService.getByEmail(expectedEmail);
        assertTrue(userByEmail.isPresent(),"User not found with email: "+expectedEmail);
        assertEquals(expectedEmail,userByEmail.get().getEmail(),"Email should be match");
        verify(userRepository,timeout(1)).getByEmail(expectedEmail);
    }

    @Test
    void filtrationAndSortingAndPagination() {
        String searchValue = "Roman";

        Pageable pageable = PageRequest.of(0, 2);
        Page<User> userPage = new PageImpl<>(loadedUsers, pageable, loadedUsers.size());

        when(userRepository.findAll(any(GenericSearchSpecification.class), eq(pageable))).thenReturn(userPage);

        try (MockedStatic<UserMapper> mockedStatic = mockStatic(UserMapper.class)) {
            mockedStatic.when(() -> UserMapper.toDTOView(any(User.class)))
                    .thenAnswer(invocation -> {
                        User user = invocation.getArgument(0);
                        UserDTOView dto = new UserDTOView();
                        dto.setId(user.getId());
                        dto.setFio(user.getLastName() + " " + user.getName());
                        dto.setLastname(user.getLastName());
                        dto.setName(user.getName());
                        return dto;
                    });

            Page<UserDTOView> result = userService.filtrationAndSortingAndPagination(pageable, searchValue);

            verify(userRepository, times(1)).findAll(any(GenericSearchSpecification.class), eq(pageable));

            assertEquals(5, result.getTotalElements());
            assertEquals("Roman", result.getContent().get(0).getName());
        }
    }


    @Test
    void shouldGetAll_ByPageable() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<User> userPage = new PageImpl<>(loadedUsers, pageable, loadedUsers.size());
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
        Page<UserDTOView> res = userService.getAll(pageable);
        verify(userRepository, times(1)).findAll(pageable);
        assertEquals(5, res.getTotalElements());
        assertEquals("Roman", res.getContent().get(0).getName());
    }

    @Test
    void getAllSelected() {
        List<User> expectedSelectedUsers = loadedUsers.stream()
                .filter(User::isSelected)
                .toList();

        when(userRepository.getAllByIsSelected(true)).thenReturn(expectedSelectedUsers);
        List<User> usersSelected = userService.getAllSelected();

        assertEquals(expectedSelectedUsers.size(),usersSelected.size(),"Size should be match");
        for (User u:usersSelected){
            assertFalse(u.isSelected(), "Every user should be unselected state after method");
        }
    }

    @Test
    void shouldSaveAll() {
        userService.saveAll(loadedUsers);
        verify(userRepository,times(1)).saveAll(loadedUsers);
    }

    @Test
    void shouldFindCityWithCounts() {
        List<Object[]> expectedCitiesCount = Arrays.asList(
                new Object[]{"Kyiv", 4},
                new Object[]{"Lviv", 10},
                new Object[]{"Rivne", 1}
        );
        when(userRepository.findAllCitiesWithCountNative()).thenReturn(expectedCitiesCount);
        List<CityCountDTO> result = userService.findCityWithCounts();

        assertEquals(3, result.size());
        assertEquals("Kyiv", result.get(0).getCity());
        assertEquals(4, result.get(0).getCount());
        verify(userRepository,times(1)).findAllCitiesWithCountNative();
    }

    @Test
    void shouldGetCountGenders() {
        boolean isManExpected = true;
        List<User> men = loadedUsers.stream()
                .filter(User::isMan)
                .toList();
        when(userRepository.getAllByIsMan(isManExpected)).thenReturn(men);
        Integer menCount = userService.getCountGenders(isManExpected);
        assertEquals(men.size(),menCount,"Size should be match");
        verify(userRepository,times(1)).getAllByIsMan(isManExpected);
    }
}