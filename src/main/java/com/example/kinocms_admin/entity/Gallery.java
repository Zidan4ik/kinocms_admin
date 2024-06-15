package com.example.kinocms_admin.entity;

import com.example.kinocms_admin.enums.GalleriesType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "galleries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String linkImage;
    private GalleriesType type;
    @ManyToOne
    private Film film;
}
