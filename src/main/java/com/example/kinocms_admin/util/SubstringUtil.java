package com.example.kinocms_admin.util;

public class SubstringUtil {
    public static String substringMark(String mark) {
        String result = "";
        if (mark.charAt(0) == '[') {
            mark = mark.substring(1, mark.length());
        }
        if (mark.charAt(mark.length() - 1) == ']') {
            mark = mark.substring(0,mark.length()-1);
        }
        result=mark;

        if (result.charAt(0) == '{') {
            result = result.substring(1, result.length());
        }
        if (result.charAt(result.length() - 1) == '}') {
            result = result.substring(0,result.length()-1);
        }
        String[] split = result.split(":");
        split[1]=split[1].substring(1,split[1].length()-1);


        return split[1];
        //[{"value":"2D"}
    }
}
