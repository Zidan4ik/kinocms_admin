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
    public CeoBlock(LanguageCode languageCode, PageType pageType, String title, String keywords, String descriptions, Film film) {
        this.languageCode = languageCode;
        this.pageType = pageType;
        this.title = title;
        this.keywords = keywords;
        this.descriptions = descriptions;
        this.film = film;
    }
}
