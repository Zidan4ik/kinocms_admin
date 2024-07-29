package com.example.kinocms_admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "halls")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;
    private String nameSchema;
    private String nameBanner;
    @Column(length = 2048)
    private String urlCeo;
    private LocalDate dateOfCreation;
    @ManyToOne
    private Cinema cinema;
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Gallery> galleryList;
    @OneToMany(mappedBy = "hall", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<CeoBlock> ceoBlocks;
    @OneToMany(mappedBy = "hall", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<PageTranslation> pageTranslations;
}
