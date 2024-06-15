package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.model.GalleryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class FilmRestController {
    @PostMapping(value = "/film/add",consumes = "multipart/form-data")
    public ResponseEntity<Object> addFilmPM(  @RequestParam("titleFilm") String title,
                                              @RequestParam("descriptionFilm") String description,
                                              @RequestParam("images") List<GalleryDTO> list
    ) {
//        ServiceResponse<FilmUnifier> response = new ServiceResponse<>("success", unifier);
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return ResponseEntity.ok("Form submitted successfully");
    }
}

