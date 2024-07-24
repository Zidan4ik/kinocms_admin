package com.example.kinocms_admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cinemas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameLogo;
    private String nameBanner;
    @Column(length = 2048)
    private String urlCeo;
    private LocalDate dateOfCreation;
    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<CeoBlock> ceoBlocks;
    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<PageTranslation> pageTranslations;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cinemas_marks",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id")
    )
    private Set<Mark> marksList;

    @OneToMany(mappedBy = "cinema",cascade = CascadeType.ALL)
    private List<Gallery> galleries;
}
