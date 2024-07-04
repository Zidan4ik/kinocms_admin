package com.example.kinocms_admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "films")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urlCEO;
    private String pathImage;
    @Lob
    @Column(columnDefinition = "text")
    private String linkTrailer;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int year;
    private LocalTime time;
    private BigDecimal budget;

    @OneToMany(mappedBy = "film")
    private List<CeoBlock> ceoBlocks;
    @OneToMany(mappedBy = "film")
    private List<PageTranslation> pageTranslations;

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
