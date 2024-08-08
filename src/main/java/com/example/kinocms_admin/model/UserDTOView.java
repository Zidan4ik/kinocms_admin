package com.example.kinocms_admin.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOView {
    private Long id;
    private String dateOfRegistration;
    private String dateOfBirthday;
    private String email;
    private String phone;
    private String fio;
    private String nickname;
    private String city;
    private Boolean isSelected;
}
