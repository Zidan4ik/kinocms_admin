package com.example.kinocms_admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "films")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Lob
    private String description;
    private String mainImage;
    private String trailer;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int durationTime;
    private int year;
    private double budget;
    @Column(nullable = true)
    private Integer ageAccess;

    @OneToOne
    private CeoBlock ceoBlock;
}
