package com.example.kinocms_admin.util;

import com.example.kinocms_admin.model.ContactDTO;
import com.example.kinocms_admin.model.ContactDTOAdd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void transformationJsonToObject() {
    }

    @Test
    void transformationJsonToObject_ShouldHandleInvalidJson() {
        ContactDTOAdd dto = new ContactDTOAdd();
        dto.setContactsTexts(
                List.of(
                        "{\"idContact\":\"2\",\"titleUkr\":\"Планета кіно\",\"addressUkr\":\"вулиця Мельника, 18\\nЛьвів, Львівська область, 79000\",\"coordinatesUkr\":\"49.85099002472568,24.02259614669443\"}"
                )
        );
        List<ContactDTO> result = JsonUtil.transformationJsonToObject(dto.getContactsTexts(), ContactDTO.class);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void transformationJsonToObject_ShouldThrowException() {
        ContactDTOAdd dto = new ContactDTOAdd();
        dto.setContactsTexts(
                List.of(
                        "{\"idContact1\":\"2\"}"
                )
        );
        List<ContactDTO> result = JsonUtil.transformationJsonToObject(dto.getContactsTexts(), ContactDTO.class);
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}