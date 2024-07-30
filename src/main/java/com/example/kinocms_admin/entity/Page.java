package com.example.kinocms_admin.entity;

import com.example.kinocms_admin.enums.PageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 15)
    private String phoneFirst;
    @Column(length = 15)
    private String phoneSecond;
    private boolean status;
    private PageType type;
    private String nameImage1;
    private String nameImage2;
    private String nameImage3;
    private String nameBanner;
    private LocalDate dateOfCreation;
    @Column(length = 2048)
    private String urlCeo;
    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    private List<Gallery> galleries;
    @OneToMany(mappedBy = "page", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CeoBlock> ceoBlocks;
    @OneToMany(mappedBy = "page", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<PageTranslation> pageTranslations;

    public Page(boolean status, PageType type, LocalDate dateOfCreation) {
        this.status = status;
        this.type = type;
        this.dateOfCreation = dateOfCreation;
    }
}
