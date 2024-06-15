package com.example.kinocms_admin.util;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class MonthTranslator {
    private static final Map<Month,String> map = new HashMap<>();

    static {
        map.put(Month.JANUARY,"Січня");
        map.put(Month.FEBRUARY,"Лютого");
        map.put(Month.MARCH,"Березня");
        map.put(Month.APRIL,"Квітня");
        map.put(Month.MAY,"Травня");
        map.put(Month.JUNE,"Червня");
        map.put(Month.JULY,"Липня");
        map.put(Month.AUGUST,"Серпня");
        map.put(Month.SEPTEMBER,"Вересня");
        map.put(Month.OCTOBER,"Жовтня");
        map.put(Month.NOVEMBER,"Листопада");
        map.put(Month.DECEMBER,"Грудня");
    }

    public static String translate(Month month){
        return map.get(month);
    }
}
