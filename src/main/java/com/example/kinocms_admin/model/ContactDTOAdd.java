package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTOAdd {
    private Long id;
    private String title;
    private String address;
    private String coordinates;
    private String nameLogo;
    private MultipartFile fileLogo;
    private Boolean status;
}
