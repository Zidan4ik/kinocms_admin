package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Mark;
import java.util.List;

public interface MarkService {
    void save(Mark mark);
    void deleteById(long id);

}
