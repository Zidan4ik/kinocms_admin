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
@Table(name = "shares")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameImage;
    private String nameBanner;
    private LocalDate dateOfCreation;
    private boolean status;
    @Column(length = 2048)
    private String urlCeo;
    @OneToMany(mappedBy = "share", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<PageTranslation> pageTranslations;
    @OneToMany(mappedBy = "share", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CeoBlock> ceoBlocks;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "shares_marks",
            joinColumns = @JoinColumn(name = "share_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id")
    )
    private Set<Mark> marksList;
}
