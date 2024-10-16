package com.example.kinocms_admin.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTOAdd {
    private Long id;
    private List<MultipartFile> contactsFiles;
    private List<String> contactsTexts;
    private List<ContactDTO> contactDTO;
    private String urlCeo;
    private String titleCeoUkr;
    private String keywordsCeoUkr;
    private String descriptionCeoUkr;
}
