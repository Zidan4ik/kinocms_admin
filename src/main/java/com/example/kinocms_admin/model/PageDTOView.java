package com.example.kinocms_admin.model;

import com.example.kinocms_admin.enums.PageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTOView {
    private Long id;
    private String title;
    private String dateOfCreation;
    private Boolean status;
    private PageType type;
}
