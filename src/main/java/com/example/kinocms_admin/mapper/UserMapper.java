package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.model.UserDTOView;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserMapper {
    public static UserDTOView toDTOView(User user) {
        UserDTOView dto = new UserDTOView();
        dto.setId(user.getId());
        dto.setFio(user.getLastName() + " " + user.getName());
        dto.setNickname(user.getNickname());
        dto.setCity(user.getCity());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setIsSelected(user.isSelected());
        dto.setIsMan(user.isMan());
        dto.setName(user.getName());
        dto.setLastname(user.getLastName());
        dto.setAddress(user.getAddress());
        dto.setCard(user.getNumberCard());

        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthdayParse = LocalDate.parse(String.valueOf(user.getDateOfBirthday()), input);
        LocalDate registrationParse = LocalDate.parse(String.valueOf(user.getDateOfRegistration()), input);

        dto.setDateOfBirthday(birthdayParse.format(output));
        dto.setDateOfRegistration(registrationParse.format(output));
        return dto;
    }
    public static User toEntity(UserDTOView dto){
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setLastName(dto.getLastname());
        user.setNickname(dto.getNickname());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setCity(dto.getCity());
        user.setNumberCard(dto.getCard());
        user.setMan(dto.getIsMan());
        user.setAddress(dto.getAddress());
        user.setDateOfBirthday(LocalDate.parse(dto.getDateOfBirthday(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        user.setDateOfRegistration(LocalDate.parse(dto.getDateOfRegistration(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        return user;
    }
}
