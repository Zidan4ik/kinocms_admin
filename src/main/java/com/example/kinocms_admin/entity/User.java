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
    private String nickname;
    private String phone;
    private String email;
    private String city;
    private String password;
    private String numberCard;
    private LanguageCode code;
    private boolean isMan;
    private LocalDate dateOfBirthday;
    private LocalDate dateOfRegistration;
    private boolean isSelected;

    @Lob
    @Column(columnDefinition = "text")
    private String address;

    public User(String name, String lastName, String nickname, String phone, String email, String city, String password, String numberCard, LanguageCode code, boolean isMan, LocalDate dateOfBirthday, LocalDate dateOfRegistration, boolean isSelected, String address) {
        this.name = name;
        this.lastName = lastName;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.password = password;
        this.numberCard = numberCard;
        this.code = code;
        this.isMan = isMan;
        this.dateOfBirthday = dateOfBirthday;
        this.dateOfRegistration = dateOfRegistration;
        this.isSelected = isSelected;
        this.address = address;
    }
}

