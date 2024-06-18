package com.example.kinocms_admin.entity;

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
    private long id;
    private String url;
    private String title;
    private String keywords;
    @Lob
    private String descriptions;
}
