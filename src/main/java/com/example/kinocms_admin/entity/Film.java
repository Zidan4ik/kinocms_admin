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
import java.util.Set;


@Entity
@Table(name = "films")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private CeoBlock ceoBlock;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genresList;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "film_mark",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id")
    )
    private Set<Mark> marksList;
    @OneToMany(mappedBy = "film", cascade = CascadeType.PERSIST)
    private List<Gallery> galleries;
}
