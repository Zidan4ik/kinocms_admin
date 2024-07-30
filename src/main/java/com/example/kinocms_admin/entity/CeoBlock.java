package com.example.kinocms_admin.entity;

import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ceo_blocks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CeoBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LanguageCode languageCode;
    private PageType pageType;
    private String title;
    private String keywords;
    @Lob
    @Column(columnDefinition = "text")
    private String descriptions;
    @ManyToOne
    private Film film;
    @ManyToOne
    private Cinema cinema;
    @ManyToOne
    private Hall hall;
    @ManyToOne
    private New newEntity;
    @ManyToOne
    private Share share;
    @ManyToOne
    private Page page;
    public CeoBlock(LanguageCode languageCode, PageType pageType, String title, String keywords, String descriptions, Film film) {
        this.languageCode = languageCode;
        this.pageType = pageType;
        this.title = title;
        this.keywords = keywords;
        this.descriptions = descriptions;
        this.film = film;
    }

    public CeoBlock(LanguageCode languageCode, PageType pageType, String title, String keywords, String descriptions, Hall hall) {
        this.languageCode = languageCode;
        this.pageType = pageType;
        this.title = title;
        this.keywords = keywords;
        this.descriptions = descriptions;
        this.hall = hall;
    }

    public CeoBlock(LanguageCode languageCode, PageType pageType, String title, String keywords, String descriptions, Cinema cinema) {
        this.languageCode = languageCode;
        this.pageType = pageType;
        this.title = title;
        this.keywords = keywords;
        this.descriptions = descriptions;
        this.cinema = cinema;
    }

    public CeoBlock(LanguageCode languageCode, PageType pageType, String title, String keywords, String descriptions, New newEntity) {
        this.languageCode = languageCode;
        this.pageType = pageType;
        this.title = title;
        this.keywords = keywords;
        this.descriptions = descriptions;
        this.newEntity = newEntity;
    }

    public CeoBlock(LanguageCode languageCode, PageType pageType, String title, String keywords, String descriptions, Share share) {
        this.languageCode = languageCode;
        this.pageType = pageType;
        this.title = title;
        this.keywords = keywords;
        this.descriptions = descriptions;
        this.share = share;
    }

    public CeoBlock(LanguageCode languageCode, PageType pageType, String title, String keywords, String descriptions, Page page) {
        this.languageCode = languageCode;
        this.pageType = pageType;
        this.title = title;
        this.keywords = keywords;
        this.descriptions = descriptions;
        this.page = page;
    }
}
