package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.model.UserDTOView;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class UserMapper {
    public static UserDTOView toDTOView(User user) {
        UserDTOView dto = new UserDTOView();
        dto.setId(user.getId());
        dto.setFio(user.getLastName() + " " + user.getName());
        dto.setNickname(user.getNickName());
        dto.setCity(user.getCity());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setIsSelected(user.isSelected());

        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthdayParse = LocalDate.parse(String.valueOf(user.getDateOfBirthday()), input);
        LocalDate registrationParse = LocalDate.parse(String.valueOf(user.getDateOfRegistration()), input);

        dto.setDateOfBirthday(birthdayParse.format(output));
        dto.setDateOfRegistration(registrationParse.format(output));
        return dto;
    }
}
