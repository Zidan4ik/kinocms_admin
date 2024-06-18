package com.example.kinocms_admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private String title;
    @Lob
    private String description;
    private String pathImage;
    private String linkTrailer;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int year;
    private LocalTime time;
    private BigDecimal budget;
    @OneToOne
    private CeoBlock ceoBlock;
}
