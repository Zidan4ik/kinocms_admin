package com.example.kinocms_admin.util;

import com.example.kinocms_admin.entity.Genre;
import com.example.kinocms_admin.entity.Mark;
import com.example.kinocms_admin.service.MarkService;
import com.example.kinocms_admin.service.serviceimp.GenreServiceImp;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class HandleDataUtil {
    public static String substringMark(String mark) {
        String result = "";
        if (mark.charAt(0) == '[') {
            mark = mark.substring(1, mark.length());
        }
        if (mark.charAt(mark.length() - 1) == ']') {
            mark = mark.substring(0, mark.length() - 1);
        }
        result = mark;

        if (result.charAt(0) == '{') {
            result = result.substring(1, result.length());
        }
        if (result.charAt(result.length() - 1) == '}') {
            result = result.substring(0, result.length() - 1);
        }
        String[] split = result.split(":");
        split[1] = split[1].substring(1, split[1].length() - 1);
        return split[1];
    }

    public static Set<Genre> findSimilarGenre(Set<Genre> genres, GenreServiceImp genreServiceImp) {
        Set<Genre> res = new HashSet<>();
        for (Genre g : genres) {
            Optional<Genre> genre = genreServiceImp.getByName(g.getName());
            if (genre.isPresent()) {
                res.add(genre.get());
            } else {
                res.add(g);
            }
        }
        return res;
    }

    public static Set<Mark> findSimilarMark(Set<Mark> marks, MarkService markService) {
        Set<Mark> res = new HashSet<>();
        if (marks != null) {
            for (Mark g : marks) {
                Optional<Mark> name = markService.getByName(g.getName());
                if (name.isPresent()) {
                    res.add(name.get());
                } else {
                    res.add(g);
                }
            }
        }
        return res;
    }
}
