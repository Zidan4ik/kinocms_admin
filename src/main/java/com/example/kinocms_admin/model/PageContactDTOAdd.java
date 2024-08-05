package com.example.kinocms_admin.model;

import com.example.kinocms_admin.enums.PageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageContactDTOAdd {
    private Long id;
    private PageType type;
    private LocalDate localDate;
    private String urlCeo;
    private String titleCeoUkr;
    private String titleCeoEng;
    private String keywordsCeoUkr;
    private String keywordsCeoEng;
    private String descriptionCeoUkr;
    private String descriptionCeoEng;
    private List<ContactDTOAdd> contactsDTO;
}
