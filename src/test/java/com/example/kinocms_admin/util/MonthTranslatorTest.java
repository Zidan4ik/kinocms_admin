package com.example.kinocms_admin.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MonthTranslatorTest {
    @InjectMocks
    private MonthTranslator monthTranslator;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testTranslate() {
        assertEquals("Січня", MonthTranslator.translate(Month.JANUARY));
        assertEquals("Лютого", MonthTranslator.translate(Month.FEBRUARY));
        assertEquals("Березня", MonthTranslator.translate(Month.MARCH));
        assertEquals("Квітня", MonthTranslator.translate(Month.APRIL));
        assertEquals("Травня", MonthTranslator.translate(Month.MAY));
        assertEquals("Червня", MonthTranslator.translate(Month.JUNE));
        assertEquals("Липня", MonthTranslator.translate(Month.JULY));
        assertEquals("Серпня", MonthTranslator.translate(Month.AUGUST));
        assertEquals("Вересня", MonthTranslator.translate(Month.SEPTEMBER));
        assertEquals("Жовтня", MonthTranslator.translate(Month.OCTOBER));
        assertEquals("Листопада", MonthTranslator.translate(Month.NOVEMBER));
        assertEquals("Грудня", MonthTranslator.translate(Month.DECEMBER));
    }

    @Test
    void testNullInput() {
        assertNull(MonthTranslator.translate(null));
    }
}