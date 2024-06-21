package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Mark;
import java.util.Set;

public interface MarkService {
    void save(Set<Mark> mark);
    void deleteById(long id);

}
