package com.example.kinocms_admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "marks",uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "marksList")
    private List<Film> films;
    @ManyToMany(mappedBy = "marksList")
    private List<Cinema> cinemas;
    @ManyToMany(mappedBy = "marksList")
    private List<New> news;
    public Mark(String name) {
        this.name = name;
    }
}
