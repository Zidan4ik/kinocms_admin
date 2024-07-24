package com.example.kinocms_admin.util;

import com.example.kinocms_admin.entity.Genre;
import com.example.kinocms_admin.entity.Mark;
import com.example.kinocms_admin.repository.GenreRepository;
import com.example.kinocms_admin.repository.MarkRepository;

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

    public static Set<Genre> findSimilarGenre(Set<Genre> genres, GenreRepository genreRepository) {
        Set<Genre> res = new HashSet<>();
        for (Genre g : genres) {
            Optional<Genre> name = genreRepository.findByName(g.getName());
            if (name.isPresent()) {
                res.add(name.get());
            } else {
                res.add(g);
            }
        }
        return res;
    }

    public static Set<Mark> findSimilarMark(Set<Mark> marks, MarkRepository markRepository) {
        Set<Mark> res = new HashSet<>();
        if (marks != null) {
            for (Mark g : marks) {
                Optional<Mark> name = markRepository.findByName(g.getName());
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
