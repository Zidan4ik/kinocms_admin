package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDtoView {
    private Long id;
    private String title;
    private String imageLogo;

    public String getPathToLogo() {
        return "/KinoCMSAdmin-R.Pravnyk/uploads/cinemas/logo/" + id + "/" + imageLogo;
    }
}
