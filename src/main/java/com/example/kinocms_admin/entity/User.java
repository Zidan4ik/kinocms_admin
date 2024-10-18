package com.example.kinocms_admin.entity;

import com.example.kinocms_admin.enums.LanguageCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

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
    private String roles;
    private boolean isMainAdmin;

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

    public User(Long id, String name, String lastName, String nickname, String phone, String email, String city, String password, String numberCard, LanguageCode code, boolean isMan, LocalDate dateOfBirthday, LocalDate dateOfRegistration, String address) {
        this.id = id;
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
        this.address = address;
    }


    public User(Long id, String name, String lastName, String nickname, String phone, String email, String city, String password, String numberCard, LanguageCode code, boolean isMan, LocalDate dateOfBirthday) {
        this.id = id;
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
    }

    public User(Long id, String name, String lastName, String nickname, String phone, String email, String city, String password, String numberCard, LanguageCode code, boolean isMan, LocalDate dateOfBirthday, boolean isSelected) {
        this.id = id;
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
        this.isSelected = isSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isMan == user.isMan && isSelected == user.isSelected && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName) && Objects.equals(nickname, user.nickname) && Objects.equals(phone, user.phone) && Objects.equals(email, user.email) && Objects.equals(city, user.city) && Objects.equals(password, user.password) && Objects.equals(numberCard, user.numberCard) && code == user.code && Objects.equals(dateOfBirthday, user.dateOfBirthday) && Objects.equals(dateOfRegistration, user.dateOfRegistration) && Objects.equals(roles, user.roles) && Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, nickname, phone, email, city, password, numberCard, code, isMan, dateOfBirthday, dateOfRegistration, isSelected, roles, address);
    }
}

