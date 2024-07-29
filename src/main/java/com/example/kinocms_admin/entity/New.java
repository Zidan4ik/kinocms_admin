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
@Table(name = "news")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class New {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameImage;
    private LocalDate dateOfCreation;
    private boolean status;
    @Column(length = 2048)
    private String urlCeo;
    @OneToMany(mappedBy = "newEntity", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<PageTranslation> pageTranslations;
    @OneToMany(mappedBy = "newEntity", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<CeoBlock> ceoBlocks;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "news_marks",
            joinColumns = @JoinColumn(name = "new_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id")
    )
    private Set<Mark> marksList;
}
