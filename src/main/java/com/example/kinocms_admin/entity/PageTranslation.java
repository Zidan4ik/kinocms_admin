package com.example.kinocms_admin.entity;

import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pages_translations")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LanguageCode languageCode;
    private PageType pageType;
    private String title;
    @Lob
    @Column(columnDefinition = "text")
    private String description;
    @Lob
    @Column(columnDefinition = "text")
    private String conditions;
    @ManyToOne
    private Film film;
    @ManyToOne
    private Cinema cinema;
    @ManyToOne
    private Hall hall;
    public PageTranslation(LanguageCode languageCode, PageType pageType, String title, String description, String conditions, Film film) {
        this.languageCode = languageCode;
        this.pageType = pageType;
        this.title = title;
        this.description = description;
        this.conditions = conditions;
        this.film = film;
    }

    public PageTranslation(LanguageCode languageCode, PageType pageType, String title, String description, String conditions, Cinema cinema) {
        this.languageCode = languageCode;
        this.pageType = pageType;
        this.title = title;
        this.description = description;
        this.conditions = conditions;
        this.cinema = cinema;
    }

    public PageTranslation(LanguageCode languageCode, PageType pageType, String description, Hall hall) {
        this.languageCode = languageCode;
        this.pageType = pageType;
        this.description = description;
        this.hall = hall;
    }
}
