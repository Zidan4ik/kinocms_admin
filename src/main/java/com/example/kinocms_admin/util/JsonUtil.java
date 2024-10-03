package com.example.kinocms_admin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class JsonUtil {
    public static <T> List<T> transformationJsonToObject(List<String> json, Class<T> class_){
        List<T> galleryList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (String s:json){
            try {
                T dto = objectMapper.readValue(s, class_);
                galleryList.add(dto);
            } catch (Exception e) {
                log.error("Exception for cover block))",e);
            }
        }
        return galleryList;
    }
}
