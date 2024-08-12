package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOTable {
    private PageResponse<UserDTOView> pageResponse;
    private String searchValue;
}
