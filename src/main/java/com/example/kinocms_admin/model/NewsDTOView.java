package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTOView {
    private Long id;
    private String name;
    private String dateOfCreation;
    private Boolean status;
}
