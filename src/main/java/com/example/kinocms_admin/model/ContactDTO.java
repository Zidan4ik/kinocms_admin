package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    private Long idContact;
    private String titleUkr;
    private String addressUkr;
    private String coordinatesUkr;
    private String imageName;
    public String getPathToLogo(){
        return "/uploads/contacts/logo/"+idContact+"/"+imageName;
    }
}
