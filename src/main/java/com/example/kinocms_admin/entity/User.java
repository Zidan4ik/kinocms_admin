package com.example.kinocms_admin.entity;

import com.example.kinocms_admin.enums.LanguageCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String nickName;
    private String phone;
    private String email;
    private String city;
    private String password;
    private String numberCard;
    private LanguageCode code;
    private boolean gender;
    private LocalDate dateOfBirthday;
    private LocalDate dateOfRegistration;
    private boolean isSelected;
    @Lob
    @Column(columnDefinition = "text")
    private String address;
}

